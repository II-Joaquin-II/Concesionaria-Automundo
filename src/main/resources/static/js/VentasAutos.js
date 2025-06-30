const modalEditar = document.getElementById('modalEditar');
modalEditar.addEventListener('show.bs.modal', function (event) {
    const button = event.relatedTarget;

    document.getElementById('edit-id').value = button.getAttribute('data-id');
    document.getElementById('edit-cliente').value = button.getAttribute('data-cliente');
    document.getElementById('edit-nombre').value = button.getAttribute('data-nombrecompleto');
    document.getElementById('edit-fecha').value = button.getAttribute('data-fecha');
    document.getElementById('edit-auto').value = button.getAttribute('data-auto');
    document.getElementById('edit-autodesc').value = button.getAttribute('data-autodesc');
    document.getElementById('edit-color').value = button.getAttribute('data-color');
    document.getElementById('edit-estado').value = button.getAttribute('data-estado');
});