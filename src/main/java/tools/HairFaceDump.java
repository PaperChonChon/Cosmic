/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import provider.DataDirectoryEntry;
import provider.DataFileEntry;
import provider.DataProvider;
import provider.DataProviderFactory;
import provider.wz.WZFiles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author Itzik
 */
public class HairFaceDump {

    public static void main(String[] args) throws IOException {
        DataProvider data;
        //data = DataProviderFactory.getDataProvider(DataProviderFactory.getWZ(Paths.get("Character.wz/Hair")));
        //data = DataProviderFactory.getDataProvider(DataProvider.getData(Paths.get("Character.wz/Hair")));
        try {
            WZFiles hairFiles = WZFiles.HAIR;
            data = DataProviderFactory.getDataProvider(hairFiles);
            DataDirectoryEntry root;
            StringBuilder sb = new StringBuilder();
            FileOutputStream out;
            out = new FileOutputStream("9999999.js", false);
            List<Integer> maleHair1 = new LinkedList<>();
            List<Integer> maleHair2 = new LinkedList<>();
            List<Integer> maleHair3 = new LinkedList<>();
            List<Integer> maleHair4 = new LinkedList<>();
            List<Integer> femaleHair1 = new LinkedList<>();
            List<Integer> femaleHair2 = new LinkedList<>();
            List<Integer> femaleHair3 = new LinkedList<>();
            System.out.println("Loading Hairs");
            root = data.getRoot();
            for (DataFileEntry topDir : root.getFiles()) {
                int id = Integer.parseInt(topDir.getName().substring(0, 8));
                if (isMaleHair(id)) {
                    if (maleHair1.size() < 0x7F) {
                        maleHair1.add(id);
                    } else {
                        if (maleHair2.size() < 0x7F) {
                            maleHair2.add(id);
                        } else {
                            if (maleHair3.size() < 0x7F) {
                                maleHair3.add(id);
                            } else {
                                maleHair4.add(id);
                            }
                        }
                    }
                }
            }

            if (maleHair2.isEmpty()) {
                maleHair2 = maleHair1;
                maleHair3 = maleHair1;
                maleHair4 = maleHair1;
            } else if (maleHair3.isEmpty()) {
                maleHair3 = maleHair1;
                maleHair4 = maleHair1;
            } else if (maleHair4.isEmpty()) {
                maleHair4 = maleHair1;
            }

            int pages = 0;
            int max_size = 127;
            for (DataFileEntry topDir : root.getFiles()) {
                int id = Integer.parseInt(topDir.getName().substring(0, 8));
                if (isHair(id)) {
                    pages ++;
                }
            }
            int remainder = pages%max_size;
            if(remainder != 0) {
                pages = pages/max_size + 1;
            }
            else {
                pages = pages/max_size;
            }
            int[][] arrayOfArrays = new int[pages][];
            int i = 0;
            int x = 0;
            int iterator = max_size;
            for (DataFileEntry topDir : root.getFiles()) {
                int id = Integer.parseInt(topDir.getName().substring(0, 8));
                if (isHair(id)) {
                    if (i == pages-1) {
                        iterator = remainder;
                    }
                    if (x < iterator) {
                        if (arrayOfArrays[i] == null) {
                            arrayOfArrays[i] = new int[iterator];
                        }
                        arrayOfArrays[i][x] = id;
                        x += 1;
                    }
                    if (x == max_size) {
                        i += 1;
                        x = 0;
                    }
                }
            }

            for (DataFileEntry topDir : root.getFiles()) {
                int id = Integer.parseInt(topDir.getName().substring(0, 8));
                if (isFemaleHair(id)) {
                    if (femaleHair1.size() < max_size) {
                        femaleHair1.add(id);
                    } else {
                        if (femaleHair2.size() < max_size) {
                            femaleHair2.add(id);
                        } else {
                            femaleHair3.add(id);
                        }
                    }
                }
            }
            if (femaleHair2.isEmpty()) {
                femaleHair2 = femaleHair1;
                femaleHair3 = femaleHair1;
            } else if (femaleHair3.isEmpty()) {
                femaleHair3 = femaleHair1;
            }
            List<Integer> hairList = new LinkedList<>();
            for (DataFileEntry topDir : root.getFiles()) {
                hairList.add(Integer.parseInt(topDir.getName().substring(0, 8)));
            }
            List<Integer> maleFace = new LinkedList<>();
            List<Integer> femaleFace = new LinkedList<>();
            System.out.println("Loading Faces");
            data = DataProviderFactory.getDataProvider(WZFiles.FACE);
            root = data.getRoot();

            int face_pages = 0;
            for (DataFileEntry topDir : root.getFiles()) {
                int id = Integer.parseInt(topDir.getName().substring(0, 8));
                if (isFace(id)) {
                    face_pages += 1;
                }
            }
            int face_remainder = face_pages%max_size;
            if(face_remainder != 0) {
                face_pages = face_pages/max_size + 1;
            }
            else {
                face_pages = face_pages/max_size;
            }
            int[][] face_arrayOfArrays = new int[face_pages][];
            int o = 0;
            int y = 0;
            int face_iterator = max_size;
            for (DataFileEntry topDir : root.getFiles()) {
                int id = Integer.parseInt(topDir.getName().substring(0, 8));
                if (isFace(id)) {
                    if (o == face_pages-1) {
                        face_iterator = face_remainder;
                    }
                    if (y < face_iterator) {
                        if (face_arrayOfArrays[o] == null) {
                            face_arrayOfArrays[o] = new int[face_iterator];
                        }
                        face_arrayOfArrays[o][y] = id;
                        y += 1;
                    }
                    if (y == max_size) {
                        o += 1;
                        y = 0;
                    }
                }
            }

            for (DataFileEntry topDir : root.getFiles()) {
                int id = Integer.parseInt(topDir.getName().substring(0, 8));
                if (isMaleFace(id)) {
                    maleFace.add(id);
                }
            }
            for (DataFileEntry topDir : root.getFiles()) {
                int id = Integer.parseInt(topDir.getName().substring(0, 8));
                if (isFemaleFace(id)) {
                    femaleFace.add(id);
                }
            }
            /*
            if (maleHair1.isEmpty() || maleHair2.isEmpty() || maleHair3.isEmpty() || maleHair4.isEmpty() || femaleHair1.isEmpty() || femaleHair2.isEmpty() || femaleHair3.isEmpty() || maleFace.isEmpty() || femaleFace.isEmpty()) {
                System.out.println("Could not find any hairs/faces in an array.");
                System.exit(0);
                return;
            }*/
            if (arrayOfArrays.length == 0 || femaleHair1.isEmpty() || femaleHair2.isEmpty() || femaleHair3.isEmpty() || maleFace.isEmpty() || femaleFace.isEmpty()) {
                System.out.println("Could not find any hairs/faces in an array.");
                System.exit(0);
                return;
            }
            List<Integer> faceList = new LinkedList<>();
            for (DataFileEntry topDir : root.getFiles()) {
                faceList.add(Integer.parseInt(topDir.getName().substring(0, 8)));
            }
            sb.append(createStyler(arrayOfArrays, pages, max_size, remainder, maleHair1, maleHair2, maleHair3, maleHair4, femaleHair1, femaleHair2, femaleHair3, face_arrayOfArrays, face_pages, face_remainder, maleFace, femaleFace, hairList, faceList));
            //sb.append(createStyler(maleHair1, maleHair2, maleHair3, maleHair4, femaleHair1, femaleHair2, femaleHair3, maleFace, femaleFace, hairList, faceList));
            out.write(sb.toString().getBytes());
        }
        catch (Exception t){
            System.err.println("An exception occurred: " + t.getMessage());
        }
    }

    public static boolean isHair(int id) {
        if (id % 10 != 0) {
            return false;
        }
        return (id >= 40000 && id <= 62000);
    }
    public static boolean isMaleHair(int id) {
        if (id % 10 != 0) {
            return false;
        }
        if (id == 33030 || id == 33160 || id == 33590) {
            return false;
        }
        if (id / 1000 == 30 || id / 1000 == 33 || id / 1000 == 35 || (id / 1000 == 32 && id >= 32370) || id / 1000 == 36 || (id / 1000 == 37 && id >= 37160 && id <= 37170) || id / 1000 == 60) {
            return true;
        }
        switch (id) {
            case 32160:
            case 32330:
            case 34740:
                return true;
        }
        return false;
    }

    public static boolean isFemaleHair(int id) {
        if (id % 10 != 0) {
            return false;
        }
        if (id == 32160 || id == 32330 || id == 34740) {
            return false;
        }
        if (id / 1000 == 31 || id / 1000 == 34 || (id / 1000 == 32 && id < 32370) || (id / 1000 == 37 && id < 37160) || id / 1000 == 38 || id / 1000 == 39 || (id / 1000 >= 40 && id / 1000 <= 59)) {
            return true;
        }
        switch (id) {
            case 33030:
            case 33160:
            case 33590:
                return true;
        }
        return false;
        //return !isMaleHair(id);
    }

    public static boolean isFace(int id) {
        if (id % 1000 >= 100) {
            return false;
        }
        return (id >= 20000 && id < 40000);
    }
    public static boolean isMaleFace(int id) {
        if (id % 1000 >= 100) {
            return false;
        }
        if (id / 1000 == 20) {
            return true;
        }
        return false;
    }

    public static boolean isFemaleFace(int id) {
        if (id % 1000 >= 100) {
            return false;
        }
        if (id / 1000 == 21) {
            return true;
        }
        return false;
        //return !isMaleFace(id);
    }

    public static boolean hairExists(int hair) {
        DataProvider data = DataProviderFactory.getDataProvider(WZFiles.HAIR);
        final DataDirectoryEntry root = data.getRoot();
        for (DataFileEntry topDir : root.getFiles()) {
            int id = Integer.parseInt(topDir.getName().substring(0, 8));
            if (id == hair) {
                return true;
            }
        }
        return false;
    }

    public static boolean faceExists(int face) {
        DataProvider data = DataProviderFactory.getDataProvider(WZFiles.FACE);
        final DataDirectoryEntry root = data.getRoot();
        for (DataFileEntry topDir : root.getFiles()) {
            int id = Integer.parseInt(topDir.getName().substring(0, 8));
            if (id == face) {
                return true;
            }
        }
        return false;
    }

    public static String createStyler(int[][] arrayOfArrays, int pages, int max_size, int remainder, List<Integer> maleHair1, List<Integer> maleHair2, List<Integer> maleHair3, List<Integer> maleHair4, List<Integer> femaleHair1, List<Integer> femaleHair2, List<Integer> femaleHair3, int[][] face_arrayOfArrays, int face_pages, int face_remainder,List<Integer> maleFace, List<Integer> femaleFace, List<Integer> hairList, List<Integer> faceList) {
        StringBuilder sb = new StringBuilder();
        List<Integer> hair = new LinkedList<>();
        List<Integer> hair_selection = new LinkedList<>();
        List<Integer> face_selection = new LinkedList<>();
        List<Integer> skin_selection = new LinkedList<>();
        List<Integer> hair_colour_selection = new LinkedList<>();
        List<Integer> skin_colour_selection = new LinkedList<>();
        int iterator = max_size;
        int v = 300;
        for (int i = 0; i < pages; i++) {
            hair.clear();
            for (int x = 0; x < iterator; x++) {
                if (i == pages-1) {
                    iterator = remainder;
                }
                //System.out.println("i: "+i);
                //System.out.println("arrayOfArrays[i][x]: "+arrayOfArrays[i][x]);
                hair.add(arrayOfArrays[i][x]);
                //System.out.println("here");
                if (x == iterator-1) {
                    addLine(sb, "   v" + v + " = " + hair.toString() + ";");
                    v += 1;
                }
            }
        }

        List<Integer> face = new LinkedList<>();
        int face_iterator = max_size;
        int v2 = 400;
        for (int i = 0; i < face_pages; i++) {
            face.clear();
            for (int x = 0; x < face_iterator; x++) {
                if (i == face_pages-1) {
                    face_iterator = face_remainder;
                }
                face.add(face_arrayOfArrays[i][x]);
                if (x == face_iterator-1) {
                    addLine(sb, "   v" + v2 + " = " + face.toString() + ";");
                    v2 += 1;
                }
            }
        }

        addLine(sb, "var v1;");
        addLine(sb, "var v2;");
        addLine(sb, "var v3;");
        addLine(sb, "var v4;");
        addLine(sb, "var v5;");
        addLine(sb, "var v6;");
        addLine(sb, "var v7;");
        addLine(sb, "var v8;");
        addLine(sb, "var v9;");
        addLine(sb, "var v10;");
        addLine(sb, "var v11;");
        addLine(sb, "var v12;");
        addLine(sb, "var v13;");
        addLine(sb, "var v14;");
        addLine(sb, "var v15;");
        addLine(sb, "var v16;");
        addLine(sb, "var v17;");
        addLine(sb, "var v18;");
        addLine(sb, "var v19;");
        addLine(sb, "var v20;");
        addLine(sb, "var v21;");
        addLine(sb, "var v22;");
        addLine(sb, "var v23;");
        addLine(sb, "var v24;");
        addLine(sb, "var v25;");
        addLine(sb, "var v26;");
        addLine(sb, "var v27;");
        addLine(sb, "");
        addLine(sb, "function start() {");
        addLine(sb, "   //Uncomment the following to lock ultimate stylist to GM players");
        addLine(sb, "   //if (!cm.getPlayer().isGM()) {");
        addLine(sb, "      //cm.sendOk(\"Nice weather is it eh? :)\\r PM the boss to unlock this!\");");
        addLine(sb, "      //cm.dispose();");
        addLine(sb, "      //return;");
        addLine(sb, "   //}");
        addLine(sb, "   v1 = 1;");
        addLine(sb, "   v2 = Array();");
        addLine(sb, "   v3 = Array();");
        addLine(sb, "   v4 = Array();");
        addLine(sb, "   v5 = Array();");
        addLine(sb, "   v6 = [0, 1, 2, 3, 4, 5, 9, 10, 11, 12, 13, 15, 16, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 32];");
        //addLine(sb, "   v7 = " + arrayOfArrays[0][0] + ";");
        addLine(sb, "   v7 = " + maleHair1.toString() + ";");
        addLine(sb, "   v8 = " + femaleHair1.toString() + ";");
        //addLine(sb, "   v9 = " + maleHair2.toString() + ";");
        addLine(sb, "   v10 = " + femaleHair2.toString() + ";");
        //addLine(sb, "   v11 = " + maleHair3.toString() + ";");
        //addLine(sb, "   v11_1 = " + maleHair4.toString() + ";");
        addLine(sb, "   v12 = " + femaleHair3.toString() + ";");
        addLine(sb, "   v13 = cm.getPlayer().getGender();");
        addLine(sb, "   v18 = 0;");
        addLine(sb, "   v19 = " + maleFace.toString() + ";");
        addLine(sb, "   v20 = " + femaleFace.toString() + ";");
        addLine(sb, "   v21 = 0;");
        addLine(sb, "   v22 = cm.getPlayer().getHair();");
        addLine(sb, "   v23 = cm.getPlayer().getFace();");
        addLine(sb, "   v25 = 0;");
        addLine(sb, "   v26 = " + hairList.toString() + ";");
        addLine(sb, "   v27 = " + faceList.toString() + ";");
        sb.append("   cm.sendSimple(\"Time for make-up!\\r\\n#L0#Skin#l");
        for (int x=1; x <= pages; x++) {
            sb.append("\\r\\n#L").append(x).append("#Haircut").append(x).append("#l");
            hair_selection.add(x);
        }
        sb.append("\\r\\n#L").append(pages+1).append("#Hair Color#l");//.append("#Face#l\\r\\n#L").append(pages+3).append("#Eye Color#l\"");
        for (int x=1; x <= face_pages; x++) {
            sb.append("\\r\\n#L").append(x+pages+1).append("#Face").append(x).append("#l");
            face_selection.add(x+pages+1);
        }
        sb.append("\\r\\n#L").append(pages+face_pages+2).append("#Eye Color#l\"");
        addLine(sb, ");");
        addLine(sb, "}");
        addLine(sb, "");
        addLine(sb, "function action(mode, type, selection) {");
        addLine(sb, "    if (mode != 1) {");
        addLine(sb, "        cm.dispose();");
        addLine(sb, "        return;");
        addLine(sb, "    }");
        addLine(sb, "    switch (v1) {");
        addLine(sb, "        case 1:");
        addLine(sb, "            v14 = selection;");
        addLine(sb, "            switch (v14) {");
        addLine(sb, "                case 0:");
        addLine(sb, "                    sendStyle(v6);");
        addLine(sb, "                    break;");
        //Hair style
        for (int x=1; x <= pages; x++) {
            addLine(sb, "                case " + x + ":");
            addLine(sb, "                    for each(v15 in v" + (299+x) + ")");
            addLine(sb, "                        if (hairExists(v15))");
            addLine(sb, "                            v2.push(v15);");
            addLine(sb, "                    sendStyle(v2);");
            addLine(sb, "                    break;");
        }
        //Hair colour
        addLine(sb, "                case " + (pages+1) + ":");
        addLine(sb, "                    for (v18; v18 < 8; v18++)");
        addLine(sb, "                        if (hairExists(v22 + v18))");
        addLine(sb, "                            v3.push(v22 + v18);");
        addLine(sb, "                    sendStyle(v3);");
        addLine(sb, "                    break;");
        //Face style
        for (int x=1; x <= face_pages; x++) {
            addLine(sb, "                case " + (x+pages+1) + ":");
            addLine(sb, "                    for each(v15 in v"+ (399+x) + ")");
            addLine(sb, "                        if (faceExists(v15))");
            addLine(sb, "                            v4.push(v15);");
            addLine(sb, "                    sendStyle(v4);");
            addLine(sb, "                    break;");
        }
        //Face colour
        addLine(sb, "                case " + (face_pages+pages+2) + ":");
        addLine(sb, "                    let numStr = v23.toString();");
        addLine(sb, "                    if (numStr.length >= 3) {");
        addLine(sb, "                       numStr = numStr.substring(0, 2) + '0' + numStr.substring(3);");
        addLine(sb, "                       v23 = parseInt(numStr, 10); }");
        addLine(sb, "                    for(v21; v21 < 9; v21++)");
        addLine(sb, "                        if (faceExists(v23 + (v21 * 100)))");
        addLine(sb, "                            v5.push(v23 + (v21 * 100));");
        addLine(sb, "                    sendStyle(v5);");
        addLine(sb, "                    break;");
        addLine(sb, "            }");
        addLine(sb, "            v1 = 2;");
        addLine(sb, "            break;");

        /*
        addLine(sb, "                case 1:");
        addLine(sb, "                    for each(v15 in v13 == 0 ? v7 : v8)");
        addLine(sb, "                        if (hairExists(v15))");
        addLine(sb, "                            v2.push(v15);");
        addLine(sb, "                    sendStyle(v2);");
        addLine(sb, "                    break;");
        addLine(sb, "                case 2:");
        addLine(sb, "                    for each(v15 in v13 == 0 ? v9 : v10)");
        addLine(sb, "                        if (hairExists(v15))");
        addLine(sb, "                            v2.push(v15);");
        addLine(sb, "                    sendStyle(v2);");
        addLine(sb, "                    break;");
        addLine(sb, "                case 3:");
        addLine(sb, "                    for each(v15 in v13 == 0 ? v11 : v12)");
        addLine(sb, "                        if (hairExists(v15))");
        addLine(sb, "                            v2.push(v15);");
        addLine(sb, "                    sendStyle(v2);");
        addLine(sb, "                    break;");
        addLine(sb, "                case 4:");
        addLine(sb, "                    for each(v15 in v13 == 0 ? v11_1 : v12)");
        addLine(sb, "                        if (hairExists(v15))");
        addLine(sb, "                            v2.push(v15);");
        addLine(sb, "                    sendStyle(v2);");
        addLine(sb, "                    break;");
        addLine(sb, "                case 5:");
        addLine(sb, "                    for (v18; v18 < 8; v18++)");
        addLine(sb, "                        if (hairExists(v22 + v18))");
        addLine(sb, "                            v3.push(v22 + v18);");
        addLine(sb, "                    sendStyle(v3);");
        addLine(sb, "                    break;");
        addLine(sb, "                case 6:");
        addLine(sb, "                    for each(v15 in v13 == 0 ? v19 : v20)");
        addLine(sb, "                        if (faceExists(v15))");
        addLine(sb, "                            v4.push(v15);");
        addLine(sb, "                    sendStyle(v4);");
        addLine(sb, "                    break;");
        addLine(sb, "                case 7:");
        addLine(sb, "                    for(v21; v21 < 9; v21++)");
        addLine(sb, "                        if (faceExists(v23 + (v21 * 100)))");
        addLine(sb, "                            v5.push(v23 + (v21 * 100));");
        addLine(sb, "                    sendStyle(v5);");
        addLine(sb, "                    break;");
        addLine(sb, "            }");
        addLine(sb, "            v1 = 2;");
        addLine(sb, "            break;");
        */

        int case_count = 0;
        addLine(sb, "        case 2:");
        addLine(sb, "            v24 = selection;");
        addLine(sb, "            switch (v14) {");

        addLine(sb, "                case 0:");
        addLine(sb, "                    cm.setSkin(v6[v24]);");
        addLine(sb, "                    break;");
        for (int i=0; i < hair_selection.size(); i++) {
            if (i == hair_selection.size()-1) {
                addLine(sb, "                case "+ hair_selection.get(i) + ":");
                addLine(sb, "                    cm.setHair(v2[v24]);");
                addLine(sb, "                    break;");
                case_count = i;
                break;
            }
            addLine(sb, "                case "+ hair_selection.get(i) + ":");
        }

        addLine(sb, "                case "+ (hair_selection.size()+1) + ":");
        addLine(sb, "                    cm.setHair(v3[v24]);");
        addLine(sb, "                    break;");

        for (int i=0; i < face_selection.size(); i++) {
            if (i == face_selection.size()-1) {
                addLine(sb, "                case "+ (face_selection.get(i)) + ":");
                addLine(sb, "                    cm.setFace(v4[v24]);");
                addLine(sb, "                    break;");
                case_count = i+1;
                break;
            }
            addLine(sb, "                case "+ face_selection.get(i) + ":");
        }

        addLine(sb, "                case " + (hair_selection.size()+face_selection.size()+2) + ":");
        addLine(sb, "                    cm.setFace(v5[v24]);");
        addLine(sb, "                    break;");
        addLine(sb, "            }");
        addLine(sb, "            cm.dispose();");
        addLine(sb, "            break;");
        addLine(sb, "        default:");
        addLine(sb, "            cm.dispose();");
        addLine(sb, "            return;");
        addLine(sb, "   }");
        addLine(sb, "}");
        addLine(sb, "");

        /*
        addLine(sb, "                case 1:");
        addLine(sb, "                case 2:");
        addLine(sb, "                case 3:");
        addLine(sb, "                    cm.setHair(v2[v24]);");
        addLine(sb, "                    break;");
        addLine(sb, "                case 4:");
        addLine(sb, "                    cm.setHair(v3[v24]);");
        addLine(sb, "                    break;");
        addLine(sb, "                case 5:");
        addLine(sb, "                    cm.setFace(v4[v24]);");
        addLine(sb, "                    break;");
        addLine(sb, "                case 6:");
        addLine(sb, "                    cm.setFace(v5[v24]);");
        addLine(sb, "                    break;");
        addLine(sb, "            }");
        addLine(sb, "            cm.dispose();");
        addLine(sb, "            break;");
        addLine(sb, "        default:");
        addLine(sb, "            cm.dispose();");
        addLine(sb, "            return;");
        addLine(sb, "   }");
        addLine(sb, "}");
        addLine(sb, "");
        */
        addLine(sb, "function sendStyle(array) {");
        addLine(sb, "   v17 = array;");
        addLine(sb, "   cm.sendStyle(\"Pick your favorite\", v17);");
        addLine(sb, "}");
        addLine(sb, "");
        addLine(sb, "function hairExists(hair) {");
        addLine(sb, "    for (v25; v25 < v26.length; v25++)");
        addLine(sb, "        if (v26[v25] == hair) {");
        addLine(sb, "            return true;");
        addLine(sb, "            break;");
        addLine(sb, "        }");
        addLine(sb, "    return false;");
        addLine(sb, "}");
        addLine(sb, "");
        addLine(sb, "function faceExists(face) {");
        addLine(sb, "    for (v25; v25 < v27.length; v25++)");
        addLine(sb, "        if (v27[v25] == face) {");
        addLine(sb, "            return true;");
        addLine(sb, "            break;");
        addLine(sb, "        }");
        addLine(sb, "    return false;");
        addLine(sb, "}");
        return sb.toString();
    }

    /*public static String createStyler(List<Integer> maleHair1, List<Integer> maleHair2, List<Integer> maleHair3, List<Integer> maleHair4, List<Integer> femaleHair1, List<Integer> femaleHair2, List<Integer> femaleHair3, List<Integer> maleFace, List<Integer> femaleFace, List<Integer> hairList, List<Integer> faceList) {
        StringBuilder sb = new StringBuilder();
        addLine(sb, "var v1;");
        addLine(sb, "var v2;");
        addLine(sb, "var v3;");
        addLine(sb, "var v4;");
        addLine(sb, "var v5;");
        addLine(sb, "var v6;");
        addLine(sb, "var v7;");
        addLine(sb, "var v8;");
        addLine(sb, "var v9;");
        addLine(sb, "var v10;");
        addLine(sb, "var v11;");
        addLine(sb, "var v12;");
        addLine(sb, "var v13;");
        addLine(sb, "var v14;");
        addLine(sb, "var v15;");
        addLine(sb, "var v16;");
        addLine(sb, "var v17;");
        addLine(sb, "var v18;");
        addLine(sb, "var v19;");
        addLine(sb, "var v20;");
        addLine(sb, "var v21;");
        addLine(sb, "var v22;");
        addLine(sb, "var v23;");
        addLine(sb, "var v24;");
        addLine(sb, "var v25;");
        addLine(sb, "var v26;");
        addLine(sb, "var v27;");
        addLine(sb, "");
        addLine(sb, "function start() {");
        addLine(sb, "   v1 = 1;");
        addLine(sb, "   v2 = Array();");
        addLine(sb, "   v3 = Array();");
        addLine(sb, "   v4 = Array();");
        addLine(sb, "   v5 = Array();");
        addLine(sb, "   v6 = [0, 1, 2, 3, 4, 5, 9, 10];");
        addLine(sb, "   v7 = " + maleHair1.toString() + ";");
        addLine(sb, "   v8 = " + femaleHair1.toString() + ";");
        addLine(sb, "   v9 = " + maleHair2.toString() + ";");
        addLine(sb, "   v10 = " + femaleHair2.toString() + ";");
        addLine(sb, "   v11 = " + maleHair3.toString() + ";");
        addLine(sb, "   v11_1 = " + maleHair4.toString() + ";");
        addLine(sb, "   v12 = " + femaleHair3.toString() + ";");
        addLine(sb, "   v13 = cm.getPlayer().getGender();");
        addLine(sb, "   v18 = 0;");
        addLine(sb, "   v19 = " + maleFace.toString() + ";");
        addLine(sb, "   v20 = " + femaleFace.toString() + ";");
        addLine(sb, "   v21 = 0;");
        addLine(sb, "   v22 = cm.getPlayer().getHair();");
        addLine(sb, "   v23 = cm.getPlayer().getFace();");
        addLine(sb, "   v25 = 0;");
        addLine(sb, "   v26 = " + hairList.toString() + ";");
        addLine(sb, "   v27 = " + faceList.toString() + ";");
        addLine(sb, "   cm.sendSimple(\"Time for make-up!\\r\\n#L0#Skin#l\\r\\n#L1#Haircut#l\" + (v13 == 0 ? ((\"\" + v7 != v9 ? \"\\r\\n#L2#Haircut2#l\" : \"\") + (\"\" + v7 != v11 ? \"\\r\\n#L3#Haircut3#l\" : \"\")  + (\"\" + v7 != v11_1 ? \"\\r\\n#L4#Haircut4#l\" : \"\")) : ((\"\" + v8 != v10 ? \"\\r\\n#L2#Haircut2#l\" : \"\") + (\"\" + v8 != v12 ? \"\\r\\n#L3#Haircut3#l\" : \"\"))) + \"\\r\\n#L5#Hair Color#l\\r\\n#L6#Face#l\\r\\n#L7#Eye Color#l\");");
        addLine(sb, "}");
        addLine(sb, "");
        addLine(sb, "function action(mode, type, selection) {");
        addLine(sb, "    if (mode != 1) {");
        addLine(sb, "        cm.dispose();");
        addLine(sb, "        return;");
        addLine(sb, "    }");
        addLine(sb, "    switch (v1) {");
        addLine(sb, "        case 1:");
        addLine(sb, "            v14 = selection;");
        addLine(sb, "            switch (v14) {");
        addLine(sb, "                case 0:");
        addLine(sb, "                    sendStyle(v6);");
        addLine(sb, "                    break;");
        addLine(sb, "                case 1:");
        addLine(sb, "                    for each(v15 in v13 == 0 ? v7 : v8)");
        addLine(sb, "                        if (hairExists(v15))");
        addLine(sb, "                            v2.push(v15);");
        addLine(sb, "                    sendStyle(v2);");
        addLine(sb, "                    break;");
        addLine(sb, "                case 2:");
        addLine(sb, "                    for each(v15 in v13 == 0 ? v9 : v10)");
        addLine(sb, "                        if (hairExists(v15))");
        addLine(sb, "                            v2.push(v15);");
        addLine(sb, "                    sendStyle(v2);");
        addLine(sb, "                    break;");
        addLine(sb, "                case 3:");
        addLine(sb, "                    for each(v15 in v13 == 0 ? v11 : v12)");
        addLine(sb, "                        if (hairExists(v15))");
        addLine(sb, "                            v2.push(v15);");
        addLine(sb, "                    sendStyle(v2);");
        addLine(sb, "                    break;");
        addLine(sb, "                case 4:");
        addLine(sb, "                    for each(v15 in v13 == 0 ? v11_1 : v12)");
        addLine(sb, "                        if (hairExists(v15))");
        addLine(sb, "                            v2.push(v15);");
        addLine(sb, "                    sendStyle(v2);");
        addLine(sb, "                    break;");
        addLine(sb, "                case 5:");
        addLine(sb, "                    for (v18; v18 < 8; v18++)");
        addLine(sb, "                        if (hairExists(v22 + v18))");
        addLine(sb, "                            v3.push(v22 + v18);");
        addLine(sb, "                    sendStyle(v3);");
        addLine(sb, "                    break;");
        addLine(sb, "                case 6:");
        addLine(sb, "                    for each(v15 in v13 == 0 ? v19 : v20)");
        addLine(sb, "                        if (faceExists(v15))");
        addLine(sb, "                            v4.push(v15);");
        addLine(sb, "                    sendStyle(v4);");
        addLine(sb, "                    break;");
        addLine(sb, "                case 7:");
        addLine(sb, "                    for(v21; v21 < 9; v21++)");
        addLine(sb, "                        if (faceExists(v23 + (v21 * 100)))");
        addLine(sb, "                            v5.push(v23 + (v21 * 100));");
        addLine(sb, "                    sendStyle(v5);");
        addLine(sb, "                    break;");
        addLine(sb, "            }");
        addLine(sb, "            v1 = 2;");
        addLine(sb, "            break;");
        addLine(sb, "        case 2:");
        addLine(sb, "            v24 = selection;");
        addLine(sb, "            switch (v14) {");
        addLine(sb, "                case 0:");
        addLine(sb, "                    cm.setSkin(v6[v24]);");
        addLine(sb, "                    break;");
        addLine(sb, "                case 1:");
        addLine(sb, "                case 2:");
        addLine(sb, "                case 3:");
        addLine(sb, "                    cm.setHair(v2[v24]);");
        addLine(sb, "                    break;");
        addLine(sb, "                case 4:");
        addLine(sb, "                    cm.setHair(v3[v24]);");
        addLine(sb, "                    break;");
        addLine(sb, "                case 5:");
        addLine(sb, "                    cm.setFace(v4[v24]);");
        addLine(sb, "                    break;");
        addLine(sb, "                case 6:");
        addLine(sb, "                    cm.setFace(v5[v24]);");
        addLine(sb, "                    break;");
        addLine(sb, "            }");
        addLine(sb, "            cm.dispose();");
        addLine(sb, "            break;");
        addLine(sb, "        default:");
        addLine(sb, "            cm.dispose();");
        addLine(sb, "            return;");
        addLine(sb, "   }");
        addLine(sb, "}");
        addLine(sb, "");
        addLine(sb, "function sendStyle(array) {");
        addLine(sb, "   v17 = array;");
        addLine(sb, "   cm.sendStyle(\"Pick your favorite\", v17);");
        addLine(sb, "}");
        addLine(sb, "");
        addLine(sb, "function hairExists(hair) {");
        addLine(sb, "    for (v25; v25 < v26.length; v25++)");
        addLine(sb, "        if (v26[v25] == hair) {");
        addLine(sb, "            return true;");
        addLine(sb, "            break;");
        addLine(sb, "        }");
        addLine(sb, "    return false;");
        addLine(sb, "}");
        addLine(sb, "");
        addLine(sb, "function faceExists(face) {");
        addLine(sb, "    for (v25; v25 < v27.length; v25++)");
        addLine(sb, "        if (v27[v25] == face) {");
        addLine(sb, "            return true;");
        addLine(sb, "            break;");
        addLine(sb, "        }");
        addLine(sb, "    return false;");
        addLine(sb, "}");
        return sb.toString();
    }*/

    public static void addLine(StringBuilder sb, String string) {
        sb.append(string).append("\r\n");
    }
}