function [rowKey, colKey] = define_initial_keys(image)
    % Получение размеров изображения
    [height, width] = size(image);

    % Вычисление всех взаимно простых чисел для строк и столбцов
    S_rows = find_coprimes(height);
    S_cols = find_coprimes(width);

    % Нахождение корреляции первой строки с остальными строками из набора S
    rowKey = find_shuffle_key(image, S_rows, height, 'row');
    
    % Нахождение корреляции первого столбца с остальными столбцами из набора S
    colKey = find_shuffle_key(image, S_cols, width, 'col');
end

function coprimes = find_coprimes(n)
    % Функция для нахождения всех взаимно простых чисел с n
    coprimes = zeros(1, n);
    index = 1;
    for i = 1:n
        if gcd(i, n) == 1
            coprimes(index) = i;
            index = index + 1;
        end
    end
    coprimes = coprimes(1:index-1);
end

function key = find_shuffle_key(image, S, n, mode)
    % Функция для нахождения ключа перетасовки на основе корреляции
    key = zeros(1, n);
    if strcmp(mode, 'row')
        baseVector = image(1, :);
        for i = 1:length(S)
            correlation = zeros(1, n);
            for j = 1:n
                correlation(j) = sum(baseVector .* circshift(image(S(i), :), [0, j-1]));
            end
            [~, idx] = min(correlation);
            key(S(i)) = idx;
        end
    elseif strcmp(mode, 'col')
        baseVector = image(:, 1);
        for i = 1:length(S)
            correlation = zeros(1, n);
            for j = 1:n
                correlation(j) = sum(baseVector .* circshift(image(:, S(i)), [j-1, 0]));
            end
            [~, idx] = min(correlation);
            key(S(i)) = idx;
        end
    end
end