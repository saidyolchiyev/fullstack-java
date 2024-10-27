import { readFile } from '/js/fileReader.js';

// Elements
const $imgName = $('[data-load="img-name"]');
const $imgChooser = $('[data-load="chooser"]');
const $productImgPreview = $('#product-img-preview');

const getFile = (fileName) =>
  new Promise((res) => {
    const xhr = new XMLHttpRequest();
    xhr.onload = () => {
      res(xhr.response);
    };
    xhr.open('GET', `http://localhost:8080/image/${fileName}`);
    xhr.responseType = 'blob';
    xhr.send();
  });

$(document).ready(() => {
  console.log('loading img ...');
  const fileName = $imgName.val();
  getFile(fileName).then((blob) => {
    const file = new File(
      [blob],
      fileName,
      { type: 'image/jpeg', lastModified: new Date().getTime() },
      'utf-8'
    );
    const container = new DataTransfer();
    container.items.add(file);
    $imgChooser[0].files = container.files;
    readFile(file).then((src) => $productImgPreview.attr('src', src));
  });
});
