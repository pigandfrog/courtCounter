//
// Created by Richard on 6/8/2017.
//

#ifndef COURTCOUNTER_HELPERMETHODS_H
#define COURTCOUNTER_HELPERMETHODS_H

#include <utility>
#include <vector>

using namespace std;

/**
 * this method will be used to get the data from the and set it up
 * will modify an array that will contain all the info of
 * element 0 = shots attempted
 * ele 3 = threes attempted;
 * ele 2 = 2's attempted;
 * ele 1 = freethrows attempted;
 *
 */

 void getShotStats(const vector <pair <int, bool> > & shotAttempt, int  shots [] ){

    for(int i = 0; i < shotAttempt.size(); i++){
        if(shotAttempt[i].first == 3) shots[3] ++;
        else if(shotAttempt[i].first == 2)shots[2]++;
        else shots[1]++;
    }
    shots[0] = shotAttempt.size();

}





#endif //COURTCOUNTER_HELPERMETHODS_H
