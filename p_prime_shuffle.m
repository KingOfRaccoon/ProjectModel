function shuffledImage = p_prime_shuffle(image)
    % Определение ключей
    [rowKey, colKey] = define_initial_keys(image);

    % Выполнение перетасовки строк
    shuffledRows = shuffle_matrix(image, rowKey, 'row');

    % Выполнение перетасовки столбцов
    shuffledImage = shuffle_matrix(shuffledRows, colKey, 'col');
end