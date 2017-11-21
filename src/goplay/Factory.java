/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goplay;

import UI.*;

/**
 *
 * @author Fernando Alvarado
 */


public class Factory {

    public Factory(int x) {
        switch (x) {
            case 1: {
                new Thread(new Splash()).start();
                break;
            }
            case 2:{
                new Principal().setVisible(true);
            }
        }
    }
}
