package com.esprit.models;

public class avis {
    private int id;
    private int note;
    private String commentaire_pos;
    private String commentaire_neg;

    public avis(int id, int note, String commentaire_pos, String commentaire_neg) {
        this.id = id;
        this.note = note;
        this.commentaire_pos = commentaire_pos;
        this.commentaire_neg = commentaire_neg;
    }

    public int getId() {
        return id;
    }

    public int getNote() {
        return note;
    }

    public String getCommentaire_pos() {
        return commentaire_pos;
    }

    public String getCommentaire_neg() {
        return commentaire_neg;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNote(int note) {
        this.note = note;
    }

    public void setCommentaire_pos(String commentaire_pos) {
        this.commentaire_pos = commentaire_pos;
    }

    public void setCommentaire_neg(String commentaire_neg) {
        this.commentaire_neg = commentaire_neg;
    }

    @Override
    public String toString() {
        return "avis{" +
                "id=" + id +
                ", note=" + note +
                ", commentaire_pos='" + commentaire_pos + '\'' +
                ", commentaire_neg='" + commentaire_neg + '\'' +
                '}';
    }
}
