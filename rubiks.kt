import com.mycompany.ImageCipher
import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO


fun main() {
    val inputImagePath = "src/main/resources/image1.jpg"
    val outputImagePath = "src/main/resources/encrypted.jpg"
    val restoredImagePath = "src/main/resources/restoredImage.jpg"

    val numberOfActions = 3

    val key = 0xAABBCC // Простой ключ для XOR шифрования
    val imageCipher = ImageCipher(key)

    // Шаг 1: Загрузка изображения
    val image = ImageIO.read(File(inputImagePath))

    // Ключ для генерации шума
    val encryptImage = imageCipher.encryptImage(image)

    // Шаг 2: Разбиение изображения на 6 равных частей
    val parts = splitImageIntoSixParts(encryptImage)

    // Шаг 3: Разбиение каждой части на 9 частей
    val subParts = parts.map { splitImageIntoNineParts(it) }

    // Шаг 4: Сборка кубика Рубика
    val cube = assembleRubiksCube(subParts)

    // Шаг 5: Применение поворотов
    applyRotations(cube, numberOfActions)

    // Шаг 6: Обратное преобразование в изображение
    val resultImage = flattenCubeToImage(cube)

//    val finishImage = Steganography.embedImage(containerImage, resultImage)

    // Шаг 7: Сохранение изображения
    ImageIO.write(resultImage, "jpg", File(outputImagePath))

//    val extractImage = Steganography.extractImage(containerImage, resultImage.width, resultImage.height)

    val eParts = splitImageIntoSixParts(resultImage)
    println("splitImageIntoSixParts")

    val esubParts = eParts.map { splitImageIntoNineParts(it) }
    println("splitImageIntoNineParts")

    // Шаг 4: Сборка кубика Рубика
    val ecube = assembleRubiksCube(esubParts)
    println("assembleRubiksCube")

    applyCounterRotations(ecube, numberOfActions)
    println("applyCounterRotations")

    val eresultImage = flattenCubeToImage(ecube)
    println("flattenCubeToImage")

    val restoredImage = imageCipher.decryptImage(eresultImage)
    println("decryptImage")

    ImageIO.write(restoredImage, "jpg", File(restoredImagePath))
    println("write restoredImage")
}

fun splitImageIntoSixParts(image: BufferedImage): List<BufferedImage> {
    val width = image.width / 3
    val height = image.height / 2
    val parts = mutableListOf<BufferedImage>()

    for (y in 0 until 2) {
        for (x in 0 until 3) {
            val part = image.getSubimage(x * width, y * height, width, height)
            parts.add(part)
        }
    }

    return parts
}

fun splitImageIntoNineParts(image: BufferedImage): List<BufferedImage> {
    val width = image.width / 3
    val height = image.height / 3
    val parts = mutableListOf<BufferedImage>()

    for (y in 0 until 3) {
        for (x in 0 until 3) {
            val part = image.getSubimage(x * width, y * height, width, height)
            parts.add(part)
        }
    }

    return parts
}

fun assembleRubiksCube(parts: List<List<BufferedImage>>): Array<Array<Array<BufferedImage>>> {
    val cube = Array(6) { Array(3) { Array(3) { BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB) } } }

    // Заполнение кубика частями изображения
    for (i in 0 until 6) {
        val face = parts[i]
        for (y in 0 until 3) {
            for (x in 0 until 3) {
                cube[i][y][x] = face[y * 3 + x]
            }
        }
    }

    return cube
}

fun applyRotations(cube: Array<Array<Array<BufferedImage>>>, numberOfActions: Int) {
    // Пример применения поворотов (реализация зависит от конкретных требований)
    // Здесь можно реализовать алгоритмы для поворотов граней кубика Рубика
    for (i in 0 until numberOfActions) {
        rotateFaceClockwise(cube, i % 6) // Поворот передней грани по часовой стрелке
    }
}

fun rotateFaceClockwise(cube: Array<Array<Array<BufferedImage>>>, faceIndex: Int) {
    // Поворот грани на 90 градусов по часовой стрелке
    val face = cube[faceIndex]
    val newFace = Array(3) { Array(3) { face[0][0] } }

    for (y in 0 until 3) {
        for (x in 0 until 3) {
            newFace[x][2 - y] = face[y][x]
        }
    }

    for (y in 0 until 3) {
        for (x in 0 until 3) {
            face[y][x] = newFace[y][x]
        }
    }

    // Обновление соседних граней
    updateAdjacentFaces(cube, faceIndex)
}

fun updateAdjacentFaces(cube: Array<Array<Array<BufferedImage>>>, faceIndex: Int) {
    // Определение соседних граней и их позиций для каждого faceIndex
    val adjacentFaces = arrayOf(
        arrayOf(1, 4, 3, 5), // Для передней грани (faceIndex = 0)
        arrayOf(0, 5, 2, 4), // Для верхней грани (faceIndex = 1)
        arrayOf(1, 4, 3, 5), // Для задней грани (faceIndex = 2)
        arrayOf(0, 5, 2, 4), // Для нижней грани (faceIndex = 3)
        arrayOf(0, 1, 2, 3), // Для левой грани (faceIndex = 4)
        arrayOf(0, 3, 2, 1)  // Для правой грани (faceIndex = 5)
    )

    val face = adjacentFaces[faceIndex]
    val temp = Array(3) { cube[face[0]][2][it] }

    for (i in 0 until 3) {
        cube[face[0]][2][i] = cube[face[1]][2 - i][2]
        cube[face[1]][2 - i][2] = cube[face[2]][0][2 - i]
        cube[face[2]][0][2 - i] = cube[face[3]][i][0]
        cube[face[3]][i][0] = temp[i]
    }
}

fun applyCounterRotations(cube: Array<Array<Array<BufferedImage>>>, numberOfActions: Int) {
    // Пример применения поворотов (реализация зависит от конкретных требований)
    // Здесь можно реализовать алгоритмы для поворотов граней кубика Рубика
    for (i in numberOfActions-1 downTo 0) {
        repeat(3) {
            rotateFaceClockwise(cube, i % 6) // Поворот передней грани по часовой стрелке
        }
    }
}


fun flattenCubeToImage(cube: Array<Array<Array<BufferedImage>>>): BufferedImage {
    val width = cube[0][0][0].width * 3 * 3
    val height = cube[0][0][0].height * 2 * 3
    val resultImage = BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)

    // Пример обратного преобразования кубика в изображение
    for (i in 0 until 6) {
        for (y in 0 until 3) {
            for (x in 0 until 3) {
                val part = cube[i][y][x]
                val graphics = resultImage.createGraphics()
                graphics.drawImage(
                    part,
                    getOffsetWidth(i, width / 3) + x * part.width,
                    getOffsetHeight(i, height / 2) + y * part.height,
                    300,
                    300,
                    null
                )
                graphics.dispose()
            }
        }
    }

    return resultImage
}

fun getOffsetWidth(i: Int, width: Int) = width * (i % 3)

fun getOffsetHeight(i: Int, height: Int) = height * (i / 3)