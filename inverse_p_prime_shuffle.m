function originalImage = inverse_p_prime_shuffle(shuffledImage, rowKey, colKey)
    % Выполнение обратной перетасовки столбцов
    unshuffledCols = inverse_shuffle_matrix(shuffledImage, colKey, 'col');

    % Выполнение обратной перетасовки строк
    originalImage = inverse_shuffle_matrix(unshuffledCols, rowKey, 'row');
end

function unshuffledMatrix = inverse_shuffle_matrix(matrix, key, mode)
    % Функция для выполнения обратной перетасовки строк или столбцов
    [height, width] = size(matrix);
    unshuffledMatrix = zeros(height, width);
    if strcmp(mode, 'row')
        for i = 1:height
            unshuffledMatrix(i, :) = circshift(matrix(i, :), [0, -key(i)+1]);
        end
    elseif strcmp(mode, 'col')
        for i = 1:width
            unshuffledMatrix(:, i) = circshift(matrix(:, i), [-key(i)+1, 0]);
        end
    end
end