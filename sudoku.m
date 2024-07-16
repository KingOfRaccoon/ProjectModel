inputImageFile = 'resources/iteration_0_arnold.jpg';
encryptedImageFile = 'resources/sudoku_iteration_1.jpg';
decryptedImageFile = 'resources/decrypted.jpg';
image = imread(inputImageFile);
 
% Scramble the image
[scrambledImage, sudokuMatrix] = scrambleImage(image);
imwrite(scrambledImage, encryptedImageFile);
 
% Unscramble the image
unscrambledImage = unscrambleImage(scrambledImage, sudokuMatrix);
imwrite(unscrambledImage, decryptedImageFile);
 
function [scrambled, sudokuMatrix] = scrambleImage(image)
    [height, width, channels] = size(image);
    scrambled = zeros(height, width, channels, 'uint8');
    sudokuMatrix = generateSudokuMatrix(width, height);

    for x = 1:width
        for y = 1:height
            newX = sudokuMatrix(x, y, 1);
            newY = sudokuMatrix(x, y, 2);
            scrambled(newY, newX, :) = image(y, x, :);
        end
    end
end
 
function unscrambled = unscrambleImage(image, sudokuMatrix)
    [height, width, channels] = size(image);
    unscrambled = zeros(height, width, channels, 'uint8');
 
    for x = 1:width
        for y = 1:height
            newX = sudokuMatrix(x, y, 1);
            newY = sudokuMatrix(x, y, 2);
            unscrambled(y, x, :) = image(newY, newX, :);
        end
    end
end
 
function matrix = generateSudokuMatrix(width, height)
    matrix = zeros(width, height, 2);
    % Generate positions and shuffle them
    positions = reshape(1:(width * height), width, height)';
    positions = positions(randperm(numel(positions)));
    % Convert positions to (x, y) pairs
    for x = 1:width
        for y = 1:height
            idx = positions((x - 1) * height + y);
            [newY, newX] = ind2sub([height, width], idx);
            matrix(x, y, :) = [newX, newY];
        end
    end
end
