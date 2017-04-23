package com.meafs.Back.l4;


import java.util.LinkedList;
import java.util.Random;

class Text {

    private final LinkedList<Line> text;

    Text(String lines){
        this(lines.split("\\n"));
    }

    Text(String[] lines){
        text = new LinkedList<>();
        for (String line : lines)
            text.add(new Line(line));
        int i = lines[1].indexOf()
    };

    char charAt(int key) throws ArrayIndexOutOfBoundsException {
        int lineIndex = key % 100;
        int positionIndex = key / 100;
        try {
            return text.get(lineIndex).charAt(positionIndex);
        }catch (ArrayIndexOutOfBoundsException e){
            throw new ArrayIndexOutOfBoundsException("No such char");
        }
    }

    public boolean isIndexValid(int index){

        int li = index%100;
        int ri = index/100;

        int lineIndex = rand.nextInt(text.size());
        int lineLength = text.get(lineIndex).length();
        int positionIndex = rand.nextInt(lineLength);
        if(lineIndex>99 || positionIndex>99)
            return generateValidIndex(rand.setSeed(rand););
            else
                return positionIndex*100+lineIndex;
    }

    private class Line{
        private final String line;

        char charAt(int index) {
            return line.charAt(index);
        }

        Line(String line){
            this.line = line;
        }

        int length(){
            return line.length();
        }
    }

    public void setText(String newText) {
            String[] lines = newText.split("\\n");
            text.clear();
            for (String line : lines)
                text.add(new Line(line));
        }

    public void setSeed(int seed){
        rand.setSeed((long)seed);
    };
}