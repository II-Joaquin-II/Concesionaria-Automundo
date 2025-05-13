document.addEventListener('DOMContentLoaded', () => {
  const colorButtons = document.querySelectorAll('.car-color');

  colorButtons.forEach(button => {
    button.addEventListener('click', () => {
      const newImg = button.getAttribute('data-img');
      const card = button.closest('.card');
      const image = card.querySelector('.car-image');
      image.src = newImg;
    });
  });
});