function shuffledMatrix = shuffle_matrix(matrix, key, mode)
    % Функция для выполнения перетасовки строк или столбцов
    [height, width] = size(matrix);
    shuffledMatrix = zeros(height, width);
    if strcmp(mode, 'row')
        for i = 1:height
            shuffledMatrix(i, :) = circshift(matrix(i, :), [0, key(i)-1]);
        end
    elseif strcmp(mode, 'col')
        for i = 1:width
            shuffledMatrix(:, i) = circshift(matrix(:, i), [key(i)-1, 0]);
        end
    end
end