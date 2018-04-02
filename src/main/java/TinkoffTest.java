import java.util.*;

class Test{

    Test(){

        //Блок объявлений
        int length,width; //длина и ширина массива
        int key; //переключатель режима заполнения первоначального поля
        Random rnd = new Random(System.currentTimeMillis()); //инициализация рандомного значения для заполнения поля 1 и 0
        ArrayList<int[][]> myList = new ArrayList<int[][]>(); //лист, хранящий в себе вид каждого острова и количество каждого из них
        int counterMas[][] = new int[1][1]; //массив для первоначального заполнения счетчика повторений островов
        counterMas[0][0] = 1;
        int countIslands=0; //счетчик островов

        //Блок ввода длины и ширины массива
        Scanner in = new Scanner(System.in);
        System.out.println("Введите кол-во строк в поле:");
        length = in.nextInt();
        System.out.println("Введите кол-во столбцов в поле:");
        width = in.nextInt();

        int islands[][] = new int [length+2][width+2]; //инициализированное нами поле
        int foundIsland[][] = new int [length+2][width+2]; //данный массив хранит в себе каждый из островов

        //Обертка поля в 0, для того, чтобы можно было смотреть на граничные элементы без выхода за пределы массива
        for (int i=0 ; i<length+2 ; i++) {
            for (int j = 0; j < width+2; j++) {
                islands[i][j]=0;
            }
        }

        //Обертка поля в 0, для того, чтобы можно было смотреть на граничные элементы без выхода за пределы массива
        for (int i=0 ; i<length+2 ; i++) {
            for (int j = 0; j < width+2; j++) {
                foundIsland[i][j]=0;
            }
        }

        System.out.println("Если хочешь заполнять поле вручную, то введи любое число кроме 0 и нажми Enter");
        System.out.println("Если хочешь чтобы поле самостоятельно заполнялось 1 и 0, то введи 0 и нажми Enter");
        key = in.nextInt();

        //Заполнение поля
        for (int i=1 ; i<length+1 ; i++)
            for (int j=1; j<width+1; j++) {
                if (key == 0) {
                    System.out.println("Введи ["+ i + "]["+ j +"] значение поля ");
                    islands[i][j] = in.nextInt();
                }
                else islands[i][j] = 0 + rnd.nextInt(2);
            }

        //Вывод поля на экран
        System.out.println("Ваше поле:");
        for (int i=1 ; i<length+1 ; i++) {
            for (int j = 1; j < width+1; j++) {
                System.out.print(islands[i][j] + " ");
            }
            System.out.println();
        }

        /*Кусочек магии
          Подсчет количества островов*/
        for (int i=1 ; i<length+1 ; i++)
            for (int j=1; j<width+1; j++) {
                if (islands[i][j] == 1) {
                    countIslands++; //количество островов
                    counter(i, j, islands, foundIsland);

                    //Добавление в лист острова(с рамкой из 0)
                    myList.add(cutMas(foundIsland,width+2,length+2));

                    //Добавление в лист счетчика для ранее добавленного острова
                    myList.add(counterMas);


                    //Самая сложная магия
                    for (int i1 = 0; i1 < myList.size(); i1+=2) {
                        for (int j1 = i1 + 2; j1 < myList.size(); j1+=2) {

                            /*Если добавленный нами остров уже есть в листе, то он удаляется из листа, работает счетчик островов одинаковой формы
                              перевернутые острова одинаковой формы считаются как одинаковые*/
                            if (Arrays.deepEquals(myList.get(i1),myList.get(j1))||
                                    Arrays.deepEquals(myList.get(i1),turnLeft90(myList.get(j1)))|| //повернутый на 90 градусов влево
                                    Arrays.deepEquals(myList.get(i1),turnLeft90(turnLeft90(myList.get(j1))))|| //повернутый на 180 градусов влево
                                    Arrays.deepEquals(myList.get(i1),turnLeft90(turnLeft90(turnLeft90(myList.get(j1)))))) { //повернутый на 270 градусов влево
                                myList.remove(j1); //удаление острова имеющего одинаковую форму с уже внесенным в лист
                                myList.remove(j1); //удаление счетчика, созданного для этого острова
                                int countMas1[][] = new int[1][1]; //создаем массив счетчик
                                countMas1[0][0]=myList.get(i1+1)[0][0]; //считываем значение хранящееся в счетчике ранее
                                countMas1[0][0]++; //прибавляем к счетчику единицу
                                myList.set(i1+1, countMas1); //заменяем старое значение счетчика новым
                            }
                        }
                    }

                    //Обнуление массива
                    for (int i2 = 1; i2 < length + 1; i2++) {
                        for (int j2 = 1; j2 < width + 1; j2++) {
                            foundIsland[i2][j2]=0;
                        }
                    }
                }
            }

        /*Вывод результата подсчета островов одинаковой формы на экран
          все значения вытягиваются из листа содержащего все острова и счетчики для каждого из них*/
        for (int i=0; i<myList.size();i++) {

            //Вывод островов
            if (i % 2 == 0) {
                if (i == 0) {
                    System.out.println("Вид " + (i + 1) + " острова");
                    for (int j = 0; j < myList.get(i).length; j++) {
                        for (int k = 0; k < myList.get(i)[j].length; k++) {
                            System.out.print(myList.get(i)[j][k] + " ");
                        }
                        System.out.println();
                    }
                    System.out.println();
                }
                else {
                    System.out.println("Вид " + (i/2+1) + " острова");
                    for (int j = 0; j < myList.get(i).length; j++) {
                        for (int k = 0; k < myList.get(i)[j].length; k++) {
                            System.out.print(myList.get(i)[j][k] + " ");
                        }
                        System.out.println();
                    }
                    System.out.println();
                }
            }

            //Вывод счетчиков
            else{
                if(i==1){
                    System.out.print("Количество островов " + i + " вида: ");
                    for (int j = 0; j < myList.get(i).length; j++) {
                        for (int k = 0; k < myList.get(i)[j].length; k++) {
                            System.out.print(myList.get(i)[j][k] + " ");
                        }
                        System.out.println();
                    }
                    System.out.println();
                }
                else {
                    System.out.print("Количество островов " + (i/2+1) + " вида: ");
                    for (int j = 0; j < myList.get(i).length; j++) {
                        for (int k = 0; k < myList.get(i)[j].length; k++) {
                            System.out.print(myList.get(i)[j][k] + " ");
                        }
                        System.out.println();
                    }
                    System.out.println();
                }
            }
        }

        //Вывод общего количества островов
        System.out.println("Общее количество островов: " + countIslands);
    }

    //Метод уничтожающий клетки одного острова
    private static void counter(int i, int j, int[][] mas1, int[][] mas2){
        if (mas1[i][j]!=1) return; //если клетка не 1 тогда нам это не интересно
        mas1[i][j]=0; //иначе мы присваиваем данной клетке значение 0
        mas2[i][j]=1; //это для сохранения вида острова

        //Рекурсивно проделываем тоже самое для соседних клеток
        counter(i+1,j,mas1,mas2);
        counter(i-1,j,mas1,mas2);
        counter(i,j+1,mas1,mas2);
        counter(i,j-1,mas1,mas2);
    }

    //Метод для обрезания массива
    private static int [][] cutMas(int[][] arr, int length, int width){

        //Объявление новых границ массива
        int iUp=0; //верхняя гранирца
        int jLeft=0; //левая граница
        int iDown=width-1; //нижняя граница
        int jRight=length-1; //правая граница

        //Вычисляем сколько строк обрезать сверху
        for (int i=0 ; i<width ; i++) {
            int sum =0;
            for (int j = 0; j < length; j++) {
                sum +=arr[i][j];
            }
            if (sum==0){
                iUp++;
            }
            else break;
        }

        //Вычисляем до какой строки обрезать
        for (int i=width-1 ; i>=0 ; i--) {
            int sum =0;
            for (int j = 0; j < length; j++) {
                sum +=arr[i][j];
            }
            if (sum==0){
                iDown--;
            }
            else break;
        }

        //Вычисляем сколько столбцов обрезать слева
        for (int j=0 ; j<length ; j++) {
            int sum =0;
            for (int i = 0; i < width; i++) {
                sum +=arr[i][j];
            }
            if (sum==0){
                jLeft++;
            }
            else break;
        }

        //Вычисляем до какого столбца обрезать
        for (int j=length-1 ; j>=0 ; j--) {
            int sum =0;
            for (int i = 0; i < width; i++) {
                sum +=arr[i][j];
            }
            if (sum==0){
                jRight--;
            }
            else break;
        }

        //Создание нового массива
        int copy = jLeft;
        int lengthMas1=iDown-iUp+1;
        int widthMas1=jRight-jLeft+1;
        int [][] mas = new int[lengthMas1][widthMas1]; //новый обрезанный массив

        //Заполнение обрезанного массива данными
        for (int i = 0; i < lengthMas1; i++) {
            for (int j = 0; j < widthMas1; j++) {
                mas[i][j]=arr[iUp][jLeft];
                jLeft++;
            }
            jLeft=copy;
            iUp++;
        }
        System.out.println();
        return mas;
    }

    //Поворот поля влево
    private int[][] turnLeft90(int[][] matrix) {
        int cols = matrix.length; //считаем строки
        int rows = matrix[0].length; //считаем колонки
        int[][] rotatedMatrix = new int[rows][cols]; //создаем массив размерностью нужной для переворота

        //Заполнение перевернутого массива
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                rotatedMatrix[(rows-1)-j][i] = matrix[i][j];
            }
        }
        return rotatedMatrix; //возвращаем перевернутое поле
    }
}

public class TinkoffTest {
    public static void main(String[] args) {
        Test myTest = new Test(); //<3
    }
}