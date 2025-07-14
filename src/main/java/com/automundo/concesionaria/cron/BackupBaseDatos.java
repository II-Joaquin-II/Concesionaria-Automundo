/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.automundo.concesionaria.cron;

import org.springframework.stereotype.Component;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class BackupBaseDatos {

    private final String user = "root";
    private final String password = "root";
    private final String database = "concesionaria_v4";
    private final String backupDir = "C:\\backupsconcesionaria"; 

    //@Scheduled(fixedRate = 20000) 
    //@Scheduled(cron = "0 * * * * *") //Segundos Minutos Horas Dia_del_mes Mes Dia_de_la_semana
    public void ejecutarBackup() {
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy_HHmmss");
        String fechaHora = sdf.format(new Date());

        String nombreArchivo = database + "_backup_" + fechaHora + ".sql";
        String rutaCompleta = backupDir + File.separator + nombreArchivo;

        File dir = new File(backupDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        List<String> comando = new ArrayList<>();
        comando.add("mysqldump"); 
        comando.add("-u" + user);
        comando.add("-p" + password);
        comando.add("--databases");
        comando.add(database);
        comando.add("-r");
        comando.add(rutaCompleta);

        try {
            System.out.println(" ----------Ejecutando...");
            ProcessBuilder pb = new ProcessBuilder(comando);
            pb.redirectErrorStream(true);

            Process proceso = pb.start();

            try (BufferedReader lector = new BufferedReader(new InputStreamReader(proceso.getInputStream()))) {
                String linea;
                while ((linea = lector.readLine()) != null) {
                    System.out.println("xxxx" + linea);
                }
            }

            int exitCode = proceso.waitFor();

            if (exitCode == 0) {
                System.out.println("Backup exitoso: " + rutaCompleta);
            } else {
                System.err.println("Error al generar backup. Código: " + exitCode);
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
