export const readFile = (file) =>
  new Promise((res) => {
    const reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = () => {
      res(reader.result);
    };
  });
