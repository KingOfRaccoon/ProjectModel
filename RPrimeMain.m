% Загрузка изображения
take_image = imread('lena.png');
take_image = rgb2gray(take_image);
take_image = im2double(take_image);

% Определение ключей
[rowKey, colKey] = define_initial_keys(take_image);

% Выполнение Р-Прайм перетасовки
shuffled_image = p_prime_shuffle(take_image);

% Вызов функции обратной перетасовки
original_image = inverse_p_prime_shuffle(shuffled_image, rowKey, colKey);

% Отображение результата
figure();
% Начальное изображение
imshow(take_image)
figure();
% Шифрованное изображение
imshow(shuffled_image)
figure();
% Дешифрованное изображение
imshow(original_image);