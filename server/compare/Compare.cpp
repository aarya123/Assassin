#include "opencv2/core/core.hpp"
#include "opencv2/contrib/contrib.hpp"
#include "opencv2/highgui/highgui.hpp"

#include <iostream>
#include <iomanip>
#include <fstream>
#include <sstream>
#include <string>

#include <sys/types.h>
#include <dirent.h>
#include <libgen.h>

using namespace cv;
using namespace std;

void saveModel(string uID, Ptr<FaceRecognizer> model) {
    stringstream s;
    s << "user_profiles/" << uID;
    model->save(s.str());
}

Ptr<FaceRecognizer> loadModel(string uID) {
    stringstream s;
    s << "user_profiles/" << uID;
    // cerr << "Loading " << uID << endl;
    Ptr<FaceRecognizer> model = createEigenFaceRecognizer(10);
    string p = s.str();
    model->load(p);
    return model;
}

void train (string uID, vector<Mat> images, vector<int> labels) {
    if (images.size() < 1) {
        //cerr << "must have 1 or more test pictures - integration failed\n";
        cout << 0;
        exit(1);
    }
    //cerr << "Initializing training\n";
    Ptr<FaceRecognizer> model = createEigenFaceRecognizer(10);
    // cerr << "Model created\n";
    // cerr << "Training...";
    model->train(images, labels);
    // cerr << "done!\n";
    saveModel(uID, model);
    //cerr << "Save complete\n";
    cout << 1;
}

int compare (Ptr<FaceRecognizer> model, Mat testImage) {
    int predictedLabel = -1;
    double confidence = 0.0; 
    model->predict(testImage, predictedLabel, confidence);
    // cerr << "Found Label: " << predictedLabel << endl;
    // cerr << "Confidence: " << confidence << endl;
    return confidence;
}

int main (int argc, const char *argv[]) {
    if (strcmp(argv[1], "-train") == 0) {
        if (argc < 3) {
            //cerr << "usage: " << argv[0] << "-train userID fileCount file1 file2 ..." << endl;
            cout << 0;
            exit(1);
        }
        if (atoi(argv[3]) < 2) {
            //cerr << "must have 2 or more test pictures\n";
            cout << 0;
            exit(1);
        }
        vector<Mat> images;
        vector<int> labels;
        cerr << "Collecting images\n";
        for (int i=4; i<(atoi(argv[3])+4); i++) {
            Mat src = imread(argv[i], 0);
            Mat dst;
            resize(src, dst, Size(640, 853), 0, 0, INTER_CUBIC);
            images.push_back(dst);
            labels.push_back(atoi(argv[2]));
        }
        train (argv[2], images, labels);
    }

    else if (strcmp(argv[1], "-predict") == 0) {
        if (argc < 3) {
            //cerr << "usage: " << argv[0] << "-predict testFile" << endl;
            cout << 0;
            exit(1);
        }
        Mat testImage = imread(argv[2], 0);
        //cerr << "Read test image\n";
        int maximize[] = {0,0};
        //cerr << "Beginning tests...";
        DIR* dirp = opendir("user_profiles");
        struct dirent* dp;
        while ((dp = readdir(dirp)) != NULL) {
            if (dp->d_type != 4) {
                Ptr<FaceRecognizer> model = loadModel(dp->d_name);
                int result = compare(model, testImage);
                //cerr << dp->d_name << ":" << result << "...";
                if (maximize[1] == 0 || maximize[0] < result) {
                    maximize[0] = result;
                    maximize[1] = atoi(dp->d_name);
                }
            }
        }
        closedir(dirp);
        //cerr << "done!" << endl;
        //cout << "Best match: " << maximize[1] << " [" << maximize[0] << "]" << endl;
        cout << maximize[1];
    }

    else {
        //cerr << "usage: " << argv[0] << "[-train | -predict]" << endl;
        cout << 0;
        exit(1);
    }
}