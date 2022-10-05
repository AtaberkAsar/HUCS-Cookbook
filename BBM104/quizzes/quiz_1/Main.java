import java.io.File;
import java.lang.Math;
import java.util.Arrays;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String args[]){
        try{
            File operationFile = new File(args[0]);
            File outputFile = new File(args[1]);

            Scanner readOperations = new Scanner(operationFile);
            FileWriter writeOutput = new FileWriter(outputFile);

            byte operation = 0; // variable used to store which operation to do next
            String line = new String("");

            while(readOperations.hasNextLine()){
                String data = readOperations.nextLine();
                data = data.trim();

                if(data.equals("Calculating Armstrong numbers")){
                    operation = 0; // 0, find Amstrong numbers
                    writeOutput.write(data);
                    line = "";
                    continue;
                }
                else if(data.equals("Ascending order sorting")){
                    operation = 1; // 1, sort in ascending order
                    writeOutput.write(data);
                    line = "";
                    continue;
                }
                else if(data.equals("Descending order sorting")){
                    operation = 2; // 2, sort in descending order
                    writeOutput.write(data);
                    line = "";
                    continue;
                }
                else if(data.equals("-1")){
                    operation = 3; // 3, finish sorting
                }
                else if(data.equals("Exit")){
                    writeOutput.write("Terminated..");
                    break;
                }

                switch(operation){
                    case 0:
                        writeOutput.write("\n");
                        long num = Long.parseLong(data);
                        for(long i = 100; i < num; i++){
                            long origNum = i;
                            int digitNum = String.valueOf(i).length();
                            long sum = 0;
                            while(origNum > 0){
                                sum += Math.pow(origNum % 10, digitNum);
                                origNum /= 10;
                            }
                            if(sum == i){
                                line += Long.toString(sum) + " ";
                            }
                        }
                        writeOutput.write(line.trim() + "\n");
                        break;
                    case 1:
                        writeOutput.write("\n");
                        line += data + " ";
                        line = line.trim();
                        int[] numArrAsc = new int[line.split(" ").length];
                        for(int i = 0; i < numArrAsc.length; i++){
                            numArrAsc[i] = Integer.parseInt(line.split(" ")[i]);
                        }
                        Arrays.sort(numArrAsc);
                        line = "";
                        for(int i = 0; i < numArrAsc.length; i++){
                            line += numArrAsc[i] + " ";
                        }
                        writeOutput.write(line.trim());
                        break;
                    case 2:
                        writeOutput.write("\n");
                        line += data + " ";
                        line = line.trim();
                        int[] numArrDesc = new int[line.split(" ").length];
                        for(int i = 0; i < numArrDesc.length; i++){
                            numArrDesc[i] = Integer.parseInt(line.split(" ")[i]);
                        }
                        Arrays.sort(numArrDesc);
                        line = "";
                        for(int i = 0; i < numArrDesc.length; i++){
                            line += numArrDesc[numArrDesc.length - 1 - i] + " ";
                        }
                        writeOutput.write(line.trim());
                        break;
                    default:
                        writeOutput.write("\n");
                }
            }

            readOperations.close();
            writeOutput.close();
        }
        catch (IOException ioExc){}
    }
}