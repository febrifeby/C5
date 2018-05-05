/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package c5;

/**
 *
 * @author feby
 * untuk mengakses database
 */
public class GejalaModel {
    int data[][] = new int[6][7];
    
    public GejalaModel()
    {
        for (int i = 0; i < data.length; i++)
            for (int j = 0; j< data[0].length ; j++)
                data[i][j] = 0;
        
        data[0][0] = data[0][5] = data[0][6] = 1;
        data[1][0] = data[1][4] = data[1][6] = 1;
        data[2][2] = data[2][4] = data[2][6] = 1;
        data[3][1] = data[3][3] = 1; data[3][6] = -1;
        data[4][1] = 1; data[4][6] = -1;
        data[5][2] = 1; data[5][6] = -1;
    }
}
