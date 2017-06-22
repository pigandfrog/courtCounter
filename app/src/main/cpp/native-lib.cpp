#include <jni.h>
#include <string>
#include <iostream>
#include "helperMethods.h"

//helpberMethods will contain the vector and utility header
//files

using namespace std;


//these are for the team A  the bool will be to tell if the shot was a make or not
//true is make, false is miss
vector <pair<int, bool > > team_A_shotsAttempts;
int team_A_3_made = 0;
int team_A_2_made = 0;
int team_A_FreeThrow = 0;
int team_A_score = 0;
string team_a_name;


//these are for team b
vector <pair <int, bool> > team_B_shotAttempts;
int team_B_3_made =0;
int team_B_2_made = 0;
int team_B_FreeThrow =0;
int team_B_score = 0;
string team_B_name;








JNIEXPORT jintArray JNICALL
Java_com_example_richard_courtcounter_MainActivity_myIntArry(JNIEnv *env, jobject instance,
                                                             jintArray arr_) {
    jint *arr = env->GetIntArrayElements(arr_, NULL);

    // TODO

    env->ReleaseIntArrayElements(arr_, arr, 0);
}


//when a shot attempt occurs weather a make or a miss this method will run
//will internally keep track of each teams score in C++




extern "C"
JNIEXPORT void JNICALL
Java_com_example_richard_courtcounter_MainActivity_attemptANative(JNIEnv *env, jobject instance,
                                                                  jint typeAttempt, jint team, jboolean madeShot) {
//0 is team a
    if(team == 0) {
        if (typeAttempt == 3) {
            team_A_shotsAttempts.push_back(make_pair(3, (int) madeShot));
            if (madeShot == JNI_TRUE) {
                team_A_score += 3;
                team_A_3_made++;
            }
        } else if (typeAttempt == 2) {
            team_A_shotsAttempts.push_back(make_pair(2, (int) madeShot));
            if (madeShot == JNI_TRUE)team_A_score += 2, team_A_2_made++;
        } else {
            team_A_shotsAttempts.push_back(make_pair(1, (int) madeShot));
            if (madeShot == JNI_TRUE) team_A_score += 1, team_A_FreeThrow++;
        }
    }
    else {
        if(typeAttempt == 3){team_B_shotAttempts.push_back(make_pair(3, (int)madeShot));
            if(madeShot == JNI_TRUE) team_B_score+=3, team_B_3_made++ ;
        }
        else if(typeAttempt == 2){team_B_shotAttempts.push_back(make_pair(2, (int)madeShot));
            if(madeShot == JNI_TRUE)team_B_score+=2, team_B_2_made++;
        }
        else {team_B_shotAttempts.push_back(make_pair(1, (int)madeShot));
            if(madeShot == JNI_TRUE)team_B_score+=1, team_B_FreeThrow++;
        }
    }


}
//this is used to remove a point
//will returns the number of points that was made on the last shot if the shot was a make.
//if shot was a miss will return the number attempted but a negative
// if the vector containing all the shot attempts is empty then 0 will be returned

extern "C"
JNIEXPORT jint JNICALL
Java_com_example_richard_courtcounter_MainActivity_removeLastShot(JNIEnv *env, jobject instance, jint team) {

   if(team == 0){

       if(!team_A_shotsAttempts.empty()) {
           pair<int, bool> shot = team_A_shotsAttempts.back();
           team_A_shotsAttempts.pop_back();
           if (shot.second == true) {
               if (shot.first == 3)team_A_3_made --;
               if (shot.first == 2)team_A_2_made --;
               else team_A_FreeThrow --;

               team_A_score -= shot.first;

               return (jint) shot.first;
           }
           return -(jint) shot.first;
       }
       return 0;

   }

    if(team == 1){

        if(!team_B_shotAttempts.empty()) {
            pair<int, bool> shot = team_B_shotAttempts.back();
            team_B_shotAttempts.pop_back();
            if (shot.second == true) {
                if (shot.first == 3)team_B_3_made --;
                if (shot.first == 2)team_B_2_made --;
                else team_B_FreeThrow --;

                team_B_score -= shot.first;
                return (jint) shot.first;
            }
            return -(jint) shot.first;
        }
        return 0;

    }

}




//will add the team score
extern "C"
JNIEXPORT jint JNICALL
Java_com_example_richard_courtcounter_MainActivity_teamAScore(JNIEnv *env, jobject instance,
                                                              jint a, jint prevScore) {
if(prevScore == 0 && a ==-1)
    return prevScore;

    jint sum = a + prevScore;

    return sum;

}




extern "C"
JNIEXPORT jint JNICALL
Java_com_example_richard_courtcounter_MainActivity_teamBScore(JNIEnv *env, jobject instance,
                                                              jint a, jint prevScore) {
    if(prevScore == 0 && a == -1)return prevScore;

    jint sum = a + prevScore;
    return  sum;

}




extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_richard_courtcounter_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}


/**
 * this method will reset all areas of the teams
 * if the removeTeams flag is not provided then it will add one as a default
 */
extern "C"
JNIEXPORT void JNICALL
Java_com_example_richard_courtcounter_MainActivity_resetAll(JNIEnv *env, jobject instance) {

    team_B_shotAttempts.clear();
    team_A_shotsAttempts.clear();
    team_B_score = 0;
    team_A_score = 0;
    team_A_3_made = 0;
    team_B_3_made = 0;
    team_A_2_made = 0;
    team_B_2_made = 0;
    team_A_FreeThrow = 0;
    team_B_FreeThrow = 0;


}



/**
 * method to help fill in the info into the variable passed in
 */
void settingVariables(vector<pair <int, bool> > & vec, jint & theScore, jint  & made1, jint &  made2,
                        jint &  made3, int team, string & teamName)
{
   if (team == 0) {
              vec = team_A_shotsAttempts
               , theScore = team_A_score
               , made1 = team_A_FreeThrow
               , made2 = team_A_2_made
               , made3 = team_A_3_made;
                teamName = team_a_name;
   }
    else {

        vec =team_B_shotAttempts,
        theScore = team_B_score
                ,made1 = team_B_FreeThrow
                ,made2 = team_B_2_made
                ,made3 = team_B_3_made;
                teamName = team_B_name;

    }


}

/**
 *
 * @param env the java environment
 * @param team the id of the team to whom we are working
 * @return a TeamStats object;
 *
 * this method will check if the vector containing the shots is empty
 * and will adjust everything for that
 */
jobject getTheStats(JNIEnv *env, jint team){

    //vec will represent
    vector<pair <int , bool> > vec;
    jint theScore;
    jint made1;
    jint made2;
    jint made3;
    string teamName;



    settingVariables(vec, theScore, made1, made2, made3,  team, teamName);




    //will be creating a new object of the TeamStats class

    //this is the java class that we will create the object from
    jclass stats = env->FindClass("com/example/richard/courtcounter/TeamStats");

    //getting the id to the constructor this I believe is getting the pointer the the
    jmethodID constructor = env->GetMethodID(stats, "<init>", "(IIIIIIIILjava/lang/String;)V");


    int attempts [4] = {0};

    //calling the method that will get the info for creating the array
    getShotStats(vec, attempts);

    //this will check if the vector is empty if it is then will put all variables in the vector as 0
    if(vec.empty()){
        attempts[1] = attempts[2] = attempts[3] = made1 = made2 = made3 = theScore = 0;
    }

    //creating the object TeamStats
    jobject theNewObject = env->NewObject(stats, constructor, attempts[1],
                                          attempts[2], attempts[3], theScore, made1, made2, made3, team, env->NewStringUTF(teamName.c_str()) );

    return  theNewObject;
}

/**
 * method for obtaining the stats for the team
 * we will fill up the stats object with the info and then return it
 */

extern "C"
JNIEXPORT jobject JNICALL
Java_com_example_richard_courtcounter_MainActivity_getStats(JNIEnv *env, jobject instance, jint team
) {
        jobject  newObject = getTheStats(env, team);
    return newObject;

}


extern "C"
JNIEXPORT jobject JNICALL
Java_com_example_richard_courtcounter_StatsActivity_getStats(JNIEnv *env, jobject instance,
        jint teamId) {

    jobject  newObject = getTheStats(env, teamId);
    return newObject;

}




/**
 * this is a method that will send in the name of the team
 */
extern "C"
JNIEXPORT void JNICALL
Java_com_example_richard_courtcounter_MainActivity_createTeam(JNIEnv *env, jobject instance,
                                                              jint teamId, jstring name_) {
    const char *name = env->GetStringUTFChars(name_, 0);
    if(teamId == 0){
        team_a_name = name;
    }
    else team_B_name = name;



    env->ReleaseStringUTFChars(name_, name);
}




