package misc.teknofest_simulation;

import jazari.factory.FactoryUtils;
import ch.dieseite.colladaloader.ColladaLoader;
import ch.dieseite.colladaloader.wrappers.ColladaModel;
import processing.core.*;
import peasy.*;
import processing.opengl.PGL;
import processing.opengl.PGraphics3D;
import processing.opengl.PJOGL;
import controlP5.*;
import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import jazari.image_processing.ImageProcess;
import jazari.matrix.CMatrix;
import static processing.core.PApplet.abs;
import static processing.core.PApplet.cos;
import static processing.core.PApplet.radians;

public class Simulation extends PApplet {

    public final float MAX_FPS = 60;                                    //Max FPS
    public final float MAX_SPEED = 7.5f;                                //Max Speed of the car  27 km/s = 7.50 m/s
//    final float MAX_SPEED = 8.33f;                                    //Max Speed of the car  30 km/s = 8.33 m/s
    float CAR_ELEVATION = -2.5f;                                        //CAMERA_HEIGHT x olduğundan toplamları sıfır oluyor bu da arabanın zeminde olduğu izlenimini sağlıyor
    final float LEFT_VIEW_ALTITUDE = 125;                               //Kameranın sahneyi kaç metre yükseklikten seyrettiği left window için
    final float RIGHT_VIEW_DEPTH = 5f;                                   //Kameranın sağ pencere için sahneye kaç metre uzaktan seyrettiği
    final float CAMERA_HEIGHT = 2.5f;                                   //Kameranın sağ pencer için height of the perspective view camera
    final float TOP_VIEW_CAR_PX = 0;
    final float TOP_VIEW_CAR_PY = 0;
    final float PERSPECTIVE_VIEW_CAR_PX = -40.5f;
    final float PERSPECTIVE_VIEW_CAR_PY = 0;
    final float PERSPECTIVE_VIEW_CAR_PZ = -45;
    final float WAITING_TIME_FOR_DURAK = 30;
    final float WAITING_TIME_FOR_TRAFFIC_LAMB = 45;
    final float WAITING_TIME_FOR_RED = 7;
    final float WAITING_TIME_FOR_YELLOW = 1;
    final float WAITING_TIME_FOR_GREEN = 7;
    final float SPEED_INCREMENT = 0.1f;
    final float SPEED_DECREMENT = 0.5f;
    final int WHITE = 0xffFFFFFF;
    final int BLACK = 0xff000000;
    int snapshot_delay = 50;
    final int NX = 2;                                                   //Number of Viewport on X dimension
    final int NY = 1;                                                   //Number of Viewport on Y dimension. Eventually we have 2 viewports
    int record_counter = 0;
    int elapsed_time = 0;
    int start_time = 0;
    ControlFont cf;
    PFont myFont;
    PFont f;
    float total_score = 0;
    float ground_xSize = 150;
    float ground_ySize = 150;
    float ground_zSize = 1;

    EntityParkur entityParkur;
//    SoundFile soundStart;
//    SoundFile soundSpeedUp;
//    SoundFile soundSpeedDown;
//    SoundFile soundEngine;
    float world_speed = 0;
    float tpx;                                             //x coordinate of the car agent for top view
    float tpy;                                             //y coordinate of the car agent for top view
    float ppx;                                             //x coordinate of the car agent for perspective view
    float ppy;                                             //y coordinate of the car agent for perspective view
    float ppz;                                             //z coordinate of the car agent for perspective view
    float car_angle;                                       //current rotation angle of the car agent
    float car_speed;                                       //current speed of the car agent
    float steer_angle;

    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy.HH.mm.ss");
    Timestamp timestamp;

    ControlP5 cp_record;
    ControlP5 cp_new_simulation;
    ControlP5 cp_auto_pilot;

    int current_time;
    AgentCar car;                                                    //the car object
    PeasyCam[] cameras;                                              //camera array
    String snapshotFolderName;
    boolean isCarEngineStart = false;
    boolean isRecord = false;
    boolean isManual = false;
    boolean isAutoPilot = false;
    boolean flagPos = false;
    private long currentFrame, lastFrame;
    float timeElapsed;
    private Map<String, String> mapObjPath = new HashMap();
    private List<Asset> listAsset = new ArrayList();
    private List<AssetNode> listNodePoint = new ArrayList();
    private Thread recordthread = null;

    /*-----------------------------------------------------------------------------------------------------------*/
    int cnt_rect = 0;
    int agentCarView = 0;
    public Robot robot;
    private String captureFolder;

    static public void main(String[] passedArgs) {
        final String sketch = Thread.currentThread().getStackTrace()[1].getClassName();
        final String[] newArgs = {"--location=0,30"};
        PApplet.main(append(newArgs, sketch));
    }

    public void runApplet() {
        final String sketch = Thread.currentThread().getStackTrace()[1].getClassName();
        final String[] newArgs = {"--location=0,30"};
        PApplet.main(append(newArgs, sketch));
    }

    /*-----------------------------------------------------------------------------------------------------------*/
    public void settings() {
        //fullScreen(P3D);
        size(1200, 600, P3D);
        smooth(8);
    }

    /*-----------------------------------------------------------------------------------------------------------*/
    public void setup() {
        try {
            robot = new Robot();
        } catch (AWTException ex) {
            Logger.getLogger(Simulation.class.getName()).log(Level.SEVERE, null, ex);
        }
        cameras = new PeasyCam[NX * NY];
        int gap = 5;

        // tiling size
        int tilex = floor((width - gap) / NX);
        int tiley = floor((height - gap) / NY);

        // viewport offset ... corrected gap due to floor()
        int offx = (width - (tilex * NX - gap)) / 2;
        int offy = (height - (tiley * NY - gap)) / 2;

        // viewport dimension
        int cw = tilex - gap;
        int ch = tiley - gap;

        // create new viewport for each camera
        for (int y = 0; y < NY; y++) {
            for (int x = 0; x < NX; x++) {
                int id = y * NX + x;
                int cx = offx + x * tilex;
                int cy = offy + y * tiley;
                if (id == 0) {
                    cameras[id] = new PeasyCam(this, 0, 0, 0, LEFT_VIEW_ALTITUDE);
                }
                if (id == 1) {
                    cameras[id] = new PeasyCam(this, 0, 0, 0, RIGHT_VIEW_DEPTH);
                    //cameras[id].setRollRotationMode();

                }
                cameras[id].setViewport(cx, cy, cw, ch); // this is the key of this whole demo      
            }
        }

        cf = new ControlFont(createFont("Arial Black", 11, true));

        cp_new_simulation = new ControlP5(this);
        cp_new_simulation.addButton("clickNewSimulation")
                .setCaptionLabel("Senaryo Yükle")
                //                .setValue(0)
                .setPosition(40, 440)
                .setSize(200, 30)
                .setFont(cf)
                .getCaptionLabel().align(ControlP5.CENTER, ControlP5.CENTER);
        cp_new_simulation.setAutoDraw(false);

        cp_record = new ControlP5(this);
        cp_record.addToggle("clickRecord")
                .setCaptionLabel("Kayıt Al")
                .setColorActive(color(255, 0, 0))
                .setPosition(40, 480)
                .setSize(200, 30)
                .setFont(cf)
                .getCaptionLabel().align(ControlP5.CENTER, ControlP5.CENTER);
        cp_record.setAutoDraw(false);

        cp_auto_pilot = new ControlP5(this);
        cp_auto_pilot.addToggle("clickAutoPilot")
                .setCaptionLabel("Otonom Sürüşü Başlat")
                .setColorActive(color(0, 127, 0))
                .setColorCaptionLabel(color(255, 255, 0))
                .setPosition(40, 520)
                .setSize(200, 30)
                .setFont(cf)
                .getCaptionLabel().align(ControlP5.CENTER, ControlP5.CENTER);
        cp_auto_pilot.setAutoDraw(false);

        myFont = createFont(System.getProperty("user.dir") + "/data/font/conthrax/conthrax-sb.ttf", 64);
        f = createFont("Arial", 16, true);
//        ColladaModel model = ColladaLoader.load(System.getProperty("user.dir") + "/data/obj/parkur/parkur_2022/parkur_2022_original.kmz", this, null);
        ColladaModel modelParkur = ColladaLoader.load(System.getProperty("user.dir") + "/data/obj/parkur/parkur_2022/parkur_2022_hep_siyah.kmz", this, null);
//        ColladaModel model = ColladaLoader.load(System.getProperty("user.dir") + "/data/obj/parkur/parkur_2022/parkur_2022_hep_siyah_park_mavi.kmz", this, null);
        entityParkur = new EntityParkur(this, modelParkur, "parkur", 0, 0);

//        frameRate(MAX_FPS);
        initialize();
    }

    /*-----------------------------------------------------------------------------------------------------------*/
    public void initialize() {

        loadObjectHashMapping();
        listNodePoint = loadNodePoints(System.getProperty("user.dir") + "/data/nodes.txt");
        tpx = TOP_VIEW_CAR_PX;
        tpy = TOP_VIEW_CAR_PY;
        ppx = PERSPECTIVE_VIEW_CAR_PX;
        ppy = PERSPECTIVE_VIEW_CAR_PY;
        ppz = PERSPECTIVE_VIEW_CAR_PZ;
        car_angle = 0;
        car_speed = 0.0f;
        steer_angle = 0;
        start_time = millis();

        if (car == null) {
            car = new AgentCar(this, 2.4f, 4, 2, 1f);
        } else {
            car = new AgentCar(this, car, 2.4f, 4, 2, 1f);
        }

        current_time = millis();
        //println(dataPath(""));
        //snapshot_delay=(int)(20/speed);
        isCarEngineStart = true;
//        soundStart = new SoundFile(this, System.getProperty("user.dir") +"/data/sound/car_start1.mp3");
//        //soundStart.play(1,0,1,0,0);
//        soundStart.play();
//
//        soundEngine=new SoundFile(this,System.getProperty("user.dir") +"/data/sound/car_engine.mp3");
//        soundEngine.play();
//        soundSpeedUp=new SoundFile(this,System.getProperty("user.dir") +"/data/sound/car_speedup.mp3");
//        soundSpeedDown=new SoundFile(this,System.getProperty("user.dir") +"/data/sound/car_speeddown.mp3");
        //myFont=createFont("font/space_ranger/spaceranger.ttf",16);
//        lastTick = millis();
        //rec=new RecordFrames();
        //rec.start();
    }

    public List<Asset> loadScenario() {
        String str = FactoryUtils.readFile();
        if (str == null || str.isEmpty()) {
            return listAsset;
        }
        listAsset.clear();
        String[] s = str.split("\n");
        for (int i = 1; i < s.length; i++) {
            String[] ss = s[i].split("=")[1].split(",");
            String filePath = "data/obj/" + mapObjPath.get(ss[0].split(":")[1]) + "/" + mapObjPath.get(ss[0].split(":")[1]) + ".obj";
            String type = mapObjPath.get(ss[0].split(":")[1]);
            String assetTypeOriginal = ss[0].split(":")[1];
            String uuid = ss[1].split(":")[1];
            int rot = Integer.parseInt(ss[2].split(":")[1]);
            if (rot < 0) {
                rot = -rot;
            } else if (rot > 0) {
                rot = -(rot - 360);
            }
            int mx = Integer.parseInt(ss[3].split(":")[1]);
            int my = Integer.parseInt(ss[4].split(":")[1]);
            float vx = Float.parseFloat(ss[5].split(":")[1]);
            float vy = Float.parseFloat(ss[6].split(":")[1]);
            Asset asset = buildAsset(filePath, assetTypeOriginal, type, vx, vy, rot);
            if (asset != null) {
                listAsset.add(asset);
            }

        }
        return listAsset;
    }

    /*-----------------------------------------------------------------------------------------------------------*/
    public void clickNewSimulation(float val) {
        initialize();
        listAsset = loadScenario();
    }

    /*-----------------------------------------------------------------------------------------------------------*/
    public void clickAutoPilot(boolean theFlag) {
    }


    /*-----------------------------------------------------------------------------------------------------------*/
    public void clickRecord(boolean theFlag) {
        if (theFlag) {
            println("Start Recording");
            String dir = FactoryUtils.getDateTime();
            FactoryUtils.makeDirectory("data");
            FactoryUtils.makeDirectory("data/obj_detection");
            FactoryUtils.makeDirectory("data/obj_detection/recorded_images");
            captureFolder = "data/obj_detection/recorded_images/" + dir;
            FactoryUtils.makeDirectory(captureFolder);
            isRecord = true;
            recordthread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        if (isRecord) {
                            captureAndSave(captureFolder);
                        }else{
                            break;
                        }
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Simulation.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    System.out.println("thread was stopped");
                }
            });
            recordthread.start();

        } else {
            println("--Stop Recording");
            isRecord = false;
            //recordthread.stop();
            try {
                Thread.sleep(3000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Simulation.class.getName()).log(Level.SEVERE, null, ex);
            }
            recordthread=null;
        }
    }

    /*-----------------------------------------------------------------------------------------------------------*/
    public void renderSceneObjects(int ID) {
        if (ID == 0) {  //left window
            pushMatrix();
            drawMap(-300, -300);
            //MyUtils.drawGrid(this,f);
            popMatrix();
            car.display(0, tpx, tpy, 0, -car_angle);
        }
        if (ID == 1) {  //right window
            pushMatrix();
            rotateX(radians(90));
            rotateZ(radians(car_angle));
            translate(0, 0, -CAMERA_HEIGHT);
            translate(ppx, ppz, 0);
            drawMap(-300, -300);
            popMatrix();
            car.display(1, 0, CAR_ELEVATION, 0, 90);
        }
    }

    /*-----------------------------------------------------------------------------------------------------------*/
    public void draw() {
//        MainProgram.FPS=(int)frameRate;

        //operate movement invariant to the fps key point is to compute elapsed time in second as float
        currentFrame = millis();
        timeElapsed = (currentFrame - lastFrame) / 1000.0f;
        lastFrame = millis();

        if (car_speed == 0) {
            car.isParking = true;
        } else {
            car.isParking = false;
        }

        keyPressedLoop();
        //lights();
        background(WHITE);

        setGLGraphicsViewport(0, 0, width, height);
        //background(#424243);
        background(0xff93A6F2);
        for (int i = 0; i < cameras.length; i++) {
            pushStyle();
            pushMatrix();
            displayScene(cameras[i], i);
            popMatrix();
            popStyle();
        }
        float dx = sin(radians(-car_angle)) * car_speed * timeElapsed;
        float dz = cos(radians(-car_angle)) * car_speed * timeElapsed;
        float temp_ppx = ppx;
        float temp_ppz = ppz;
        temp_ppx -= dx;
        temp_ppz += dz;
        ppx -= dx;
        ppz += dz;
        tpx = -ppx;
        tpy = -ppz;
        world_speed = car_speed * 3.6f;                         //convert m/s to km/h

        car_angle += steer_angle * timeElapsed * 30;
        //decaying of steer angle with time
        steer_angle *= 0.93f;
        if (abs(steer_angle) < 0.01f) {
            steer_angle = 0;
            car.isTurnLeft = false;
            car.isTurnRight = false;
        }
        if (isRecord) {

        }

    }

    
    
    int cnt = 0;
    final int CAPTURE_DELAY = 200;
    int currentCaptureTime = millis();

    public void captureAndSave(String dir) {
        if (millis() - currentCaptureTime > CAPTURE_DELAY) {
            currentCaptureTime = millis();
            cnt++;
            BufferedImage img_cameraTrafikLevha = robot.createScreenCapture(new Rectangle(600, 150, 585, 350));
            File file_cameraTrafikLevha = new File(dir + "/" + cnt + "_screen.png");
            try {
                ImageIO.write(img_cameraTrafikLevha, "png", file_cameraTrafikLevha);
            } catch (IOException ex) {
                Logger.getLogger(Simulation.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    boolean flagZoom = false;

    public void displayScene(PeasyCam cam, int ID) {
        int[] viewport = cam.getViewport();
        int x = viewport[0];
        int y = viewport[1];
        int w = viewport[2];
        int h = viewport[3];
        int y_inv = height - y - h; // inverted y-axis

        // scissors-test and viewport transformation
        setGLGraphicsViewport(x, y_inv, w, h);
        // modelview - using camera state
        cam.feed();

        // projection - using camera viewport
        perspective(radians(60), w / (float) h, 1, 5000);
        stroke(0);
        strokeWeight(1);
        if (!flagZoom && car_speed > 0 && ID == 1) {
            //cam.reset();
            cam.setDistance(1.5);
            println(cam.getDistance());
            flagZoom = true;
        } else if (flagZoom && car_speed == 0 && ID == 1) {
            //cam.reset();
            cam.setDistance(5);
            println(cam.getDistance());
            flagZoom = false;
        }
        renderSceneObjects(ID);

        // screen-aligned 2D HUD
        cam.beginHUD();
        rectMode(CORNER);
        long t = (millis() - start_time) / 1000;
        textFont(myFont, 14);
        if (ID == 0) {
            fill(0);
            rect(0, 0, w, 25);
            fill(255, 255, 0);
            text("Üst Bakış, Geçen Süre:" + t + " s , FPS:" + (int) frameRate, 10, 15);

            hint(DISABLE_DEPTH_TEST);
            cp_record.draw();
            cp_new_simulation.draw();
            cp_auto_pilot.draw();
            hint(ENABLE_DEPTH_TEST);
        }
        if (ID == 1) {
            fill(0);
            rect(0, 0, w, 25);
            rect(w / 2 - 40, h - 50, 80, 50);
            fill(255, 255, 0);
            text("Perspektif Bakış ", 10, 15);
            String s = "" + nfc(world_speed, 1);
            text(s, (w - textWidth(s)) / 2, h - 35);
            strokeWeight(3);
            stroke(255, 0, 0);

            float px = 30 * cos(radians(90 + 90 * steer_angle));
            float py = 30 * sin(radians(90 + 90 * steer_angle));
            line(w / 2, h, w / 2 + px, h - py);
            textFont(myFont, 24);
        }
        strokeWeight(3);
        stroke(0, 0, 255);
        noFill();
        rect(0, 0, w, h);
        cam.endHUD();
    }

    /*-----------------------------------------------------------------------------------------------------------*/
    public void drawMap(float px, float py) {
        pushStyle();
        pushMatrix();

        //ortadaki küp ve x,y,z, eksenleri çiziliyor
        translate(0, 0, 0);
        stroke(255);
        fill(127);
        box(3);
        strokeWeight(3);
        stroke(255, 0, 0);
        line(0, 0, 0, 10, 0, 0);
        stroke(0, 255, 0);
        line(0, 0, 0, 0, 10, 0);
        stroke(0, 0, 255);
        line(0, 0, 0, 0, 0, 10);
        strokeWeight(1);
        stroke(0);

        //parkur çiziliyor
        entityParkur.display();

        for (Asset asset : listAsset) {
            asset.display();
        }
        popMatrix();
        popStyle();
    }

    /*-----------------------------------------------------------------------------------------------------------*/
    boolean isGeriGeri = false;
    int geriGeriCount = 0;

    public void keyPressedLoop() {
        if (keyPressed) {
            if (key == CODED) {
//                if (keyCode == ALT) {
//                    MainSimulationProgram.isCollisionMatrixRecord = !MainSimulationProgram.isCollisionMatrixRecord;
//                    System.out.println("MainSimulationProgram.isCollisionMatrixRecord = " + MainSimulationProgram.isCollisionMatrixRecord);
//                }
                if (keyCode == LEFT) {
                    if (car_speed == 0) {
                        return;
                    }
                    car.isTurnLeft = true;
                    car.isTurnRight = false;
                    if (car_speed > 0.1f) {
                        steer_angle += 0.1f;
                    } else {
                        steer_angle -= 0.1f;
                    }
                }
                if (keyCode == RIGHT) {
                    if (car_speed == 0) {
                        return;
                    }
                    car.isTurnRight = true;
                    car.isTurnLeft = false;
                    if (car_speed > 0.1f) {
                        steer_angle -= 0.1f;
                    } else {
                        steer_angle += 0.1f;
                    }
                }
                if (keyCode == UP) {
                    geriGeriCount = 0;
                    car.isParking = false;
                    if (car_speed < MAX_SPEED) {
                        car_speed += SPEED_INCREMENT;
                    }
//                    soundSpeedUp.stop();
//                    soundSpeedDown.stop();
//                    soundSpeedUp.play();
                }
                if (keyCode == DOWN) {
                    car.isBraking = true;
                    if (car_speed > 0) {
                        car_speed -= SPEED_DECREMENT;
                    } else {
                        if (++geriGeriCount < 20) {
                            car_speed = 0;
                            car.isParking = true;
                        } else if (car_speed > -8.2f) {
                            car_speed -= SPEED_DECREMENT;
                        }
                    }
//                    soundSpeedDown.stop();
//                    soundSpeedUp.stop();
//                    soundSpeedDown.play();
                }
//                System.out.println("car_speed = " + car_speed);
//                MainSimulationProgram.car_speed = car_speed;
            }
        }
    }

    /*-----------------------------------------------------------------------------------------------------------*/
    public void mousePressed() {
        if (mouseButton == LEFT) // left button
        {
            if (mouseEvent.getClickCount() == 2) {  // double-click
                println("double-click");
            }
        } else if (mouseButton == RIGHT) // right button
        {
            println("right");
        }
    }

    /*-----------------------------------------------------------------------------------------------------------*/
    public void setGLGraphicsViewport(int x, int y, int w, int h) {
        PGraphics3D pg = (PGraphics3D) this.g;
        PJOGL pgl = (PJOGL) pg.beginPGL();
        pg.endPGL();
        pgl.enable(PGL.SCISSOR_TEST);
        pgl.scissor(x, y, w, h);
        pgl.viewport(x, y, w, h);
    }

    private void loadObjectHashMapping() {
        mapObjPath.clear();
        mapObjPath.put("lamba_trafik", "trafik_isiklari");
        mapObjPath.put("levha_dur", "trafik_levha_dur");
        mapObjPath.put("levha_durak", "trafik_levha_durak");
        mapObjPath.put("levha_engel", "trafik_levha_engel");
        mapObjPath.put("levha_girilmez", "trafik_levha_giris_yok");
        mapObjPath.put("levha_hayvan", "trafik_levha_hayvan");
        mapObjPath.put("levha_ileri_veya_saga_yol", "trafik_levha_ileri_veya_saga_mecburi_yol");
        mapObjPath.put("levha_ileri_veya_sola_yol", "trafik_levha_ileri_veya_sola_mecburi_yol");
        mapObjPath.put("levha_park", "trafik_levha_park");
        mapObjPath.put("levha_park_yapilmaz", "trafik_levha_park_etmek_yasaktir");
        mapObjPath.put("levha_park_yapilmaz_2", "trafik_levha_park_etmek_yasaktir_2");
        mapObjPath.put("levha_engelli", "trafik_levha_engelli");
        mapObjPath.put("levha_saga_donulmez", "trafik_levha_saga_donulmez");
        mapObjPath.put("levha_saga_mecburi_yol", "trafik_levha_ileriden_saga_mecburi_yol");
        mapObjPath.put("levha_sola_donulmez", "trafik_levha_sola_donulmez");
        mapObjPath.put("levha_sola_mecburi_yol", "trafik_levha_ileriden_sola_mecburi_yol");
        mapObjPath.put("levha_tasit_trafigine_kapali", "trafik_levha_trafige_kapali");
        mapObjPath.put("levha_yaya_gecidi", "trafik_levha_yaya_gecidi");
        mapObjPath.put("levha_yol_calismasi", "trafik_levha_yol_calismasi");
        mapObjPath.put("node_bitis", "node_bitis");
        mapObjPath.put("node_pin_point", "node_pin_point");
        mapObjPath.put("yaya_cocuk", "");
        mapObjPath.put("yaya_insan", "yaya_insan");
        mapObjPath.put("yaya_yasli", "");
        mapObjPath.put("zemin_durak", "zemin_durak_siyah");
        mapObjPath.put("zemin_engel", "zemin_engel");
        mapObjPath.put("zemin_yaya_gecidi", "zemin_yaya_gecidi");
    }

    private Asset buildAsset(String filePath, String assetTypeOriginal, String type, float vx, float vy, int rot) {
        Asset ret = null;
        if (assetTypeOriginal.contains("levha_")) {
            ret = new AssetTrafficLevha(this, filePath, type, vx, vy, rot);
        } else if (assetTypeOriginal.contains("zemin_")) {
            ret = new AssetZemin(this, filePath, type, vx, vy, rot);
        } else if (assetTypeOriginal.contains("lamba_")) {
            ret = new AssetTrafficLight(this, filePath, type, vx, vy, rot);
        } else if (assetTypeOriginal.contains("yaya_")) {
            ret = new AssetYaya(this, filePath, type, vx, vy, rot);
        }
        return ret;
    }

    private List<AssetNode> loadNodePoints(String path) {
        listNodePoint.clear();
        String[] s = FactoryUtils.readFile(path).split("\n");
        //ilkin tüm nodelar yapılıyor ardından neighborlar için bir daha döngüye giriyor
        for (int i = 1; i < s.length; i++) {
            String[] ss = s[i].split("=")[1].split(",");
            String filePath = "data/obj/" + mapObjPath.get(ss[0].split(":")[1]) + "/" + mapObjPath.get(ss[0].split(":")[1]) + ".obj";
            String type = mapObjPath.get(ss[0].split(":")[1]);
            String uuid = ss[1].split(":")[1];
            int rot = -Integer.parseInt(ss[2].split(":")[1]);
            int mx = Integer.parseInt(ss[3].split(":")[1]);
            int my = Integer.parseInt(ss[4].split(":")[1]);
            float vx = Float.parseFloat(ss[5].split(":")[1]);
            float vy = Float.parseFloat(ss[6].split(":")[1]);
            AssetNode np = new AssetNode(this, uuid, filePath, type, vx, vy, rot, false);
            listNodePoint.add(np);
        }
        for (int i = 1; i < s.length; i++) {
            String[] ss = s[i].split("=")[1].split(",");
            if (ss.length > 7) {
                String uuid = ss[1].split(":")[1];
                AssetNode np = getNode(uuid);
                String[] uuids = ss[7].split(";");
                for (int j = 0; j < uuids.length; j++) {
                    np.listNeighbor.add(getNode(uuids[j]));
                }
            }
        }

        return listNodePoint;
    }

    private AssetNode getNode(String uuid) {
        for (AssetNode node : listNodePoint) {
            if (node.uuid.equals(uuid)) {
                return node;
            }
        }
        return null;
    }

}
