package com.myroslavderevyanko.googleMatrix;

public class Rows
{

        private Elements[] elements;

        public Elements[] getElements ()
        {
            return elements;
        }

        public void setElements (Elements[] elements)
        {
            this.elements = elements;
        }

        @Override
        public String toString()
        {
            return "ClassPojo [elements = "+elements+"]";
        }
}


