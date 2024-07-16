% Загрузка изображения
take_image = imread('lena.png');  
inputImage = rgb2gray(take_image);
inputImage = im2double(inputImage);
figure;
imshow(inputImage)

% Количество итераций
iterations = 33;
% Выполнение преобразования Арнольда
% Для возврата изображения нужно использовать формулу для определения
% кол-ва итераций: K = 3 * размерность квадратного изображения
figure;
for iteration = 1:iterations
    outputImage = arnoldTransform(inputImage, iteration);
    nexttile;
    imshow(outputImage);
    title(['Итерация ', num2str(iteration)]);
end