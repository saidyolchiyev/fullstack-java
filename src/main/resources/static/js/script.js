import { readFile } from '/js/fileReader.js';

// Elements
const $bg = $('.bg');
const $search = $('#search');
const $productImgChooser = $('#product-img-chooser');
const $previewModal = $('#preview-modal');

$(document).ready(() => {
  $bg.css({
    background: `url(${$bg
      .find('img')
      .attr('src')}) fixed center / cover no-repeat`,
  });

  $search.on('keyup', function () {
    const val = $(this).val().toLowerCase();
    $('#products-table tbody tr').filter(function () {
      $(this).toggle(
        $(this).find('.model').text().toLowerCase().indexOf(val) > -1 ||
          $(this).find('.brand').text().toLowerCase().indexOf(val) > -1
      );
    });
  });

  $('.modal-toggle').click(() => {
    if (!$previewModal.hasClass('active') && !$productImgChooser[0].files[0])
      return;
    $previewModal.toggleClass('active');
  });

  $('#limit').change(() => loadProductItems());

  const loadProductItems = () => {
    const limit = $('#limit').val();
    let n = 0;
    $('.product-item').filter(function () {
      n++;
      if (n > limit) $(this).hide();
      else $(this).show();
    });
  };

  $('[data-target]').change(function () {
    const $this = $(this);
    const $target = $($this.data('target'));
    const file = $this[0].files[0];
    readFile(file).then((src) => {
      $target.attr('src', src);
    });
  });
});
