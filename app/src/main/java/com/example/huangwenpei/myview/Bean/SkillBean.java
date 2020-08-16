package com.example.huangwenpei.myview.Bean;

public class SkillBean {
    private float min;//最值
    private float max;//最值
    private float ratio;//值效权重
    private float value;//值
    private String name;

    public SkillBean(float min, float max, float ratio, float value,String name) {
        this.min = min;
        this.max = max;
        this.ratio = ratio;
        this.value = value;
        this.name = name;
    }

    public float getMin() {
        return min;
    }

    public void setMin(float min) {
        this.min = min;
    }

    public float getMax() {
        return max;
    }

    public void setMax(float max) {
        this.max = max;
    }

    public float getRatio() {
        return ratio;
    }

    public void setRatio(float ratio) {
        this.ratio = ratio;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
