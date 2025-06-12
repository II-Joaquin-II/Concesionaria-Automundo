/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */


document.addEventListener('DOMContentLoaded', function () {
  const chatButton = document.getElementById('chat-button');
  const chatBox = document.getElementById('chat-box');
  const inputField = document.getElementById('user-input');
  const chatMessages = document.getElementById('chat-messages');

  chatButton.addEventListener('click', () => {
    chatBox.style.display = chatBox.style.display === 'none' ? 'flex' : 'none';
  });

  inputField.addEventListener('keydown', async (e) => {
    if (e.key === 'Enter' && inputField.value.trim() !== '') {
      const userMessage = inputField.value;
      appendMessage('Tú', userMessage);
      inputField.value = '';

      const response = await fetch('/chat', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ message: userMessage })
      });

      const data = await response.json();
      appendMessage('Bot', data.reply);
    }
  });

  function appendMessage(sender, text) {
    const message = document.createElement('div');
    message.textContent = `${sender}: ${text}`;
    chatMessages.appendChild(message);
    chatMessages.scrollTop = chatMessages.scrollHeight;
  }
});



