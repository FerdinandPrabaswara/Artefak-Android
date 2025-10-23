package com.example.thesis3.data;

import com.example.thesis3.R;
import com.example.thesis3.model.Post;

import java.util.ArrayList;
import java.util.List;

public class DummyPosts {

    public static List<Post> getPosts() {
        List<Post> posts = new ArrayList<>();

        posts.add(new Post("Dito", "Hari ini kena php ferdinand, guys", R.drawable.ic_profile));
        posts.add(new Post("Genta", "Love for me is like a hypothesis. It is neither a fact nor a conclusion. It is a hypothesis that needs to be validated through experiments. If you sense love around someone you just met, you need to validate it. Love should be seen, heard, and felt. Those three parameters are the data that need to be analyzed in order to give a new information for the researcher. If love is tested in various forms and conditions, you will eventually get an understanding of true love. At that point, you found the one. It is no longer “may love find me…”, but rather “you find love through curtain experiments”.", R.drawable.ic_profile));
        posts.add(new Post("Naufal", "pcc slur", R.drawable.ic_profile));
        posts.add(new Post("Yasir", "Pusing kali aku skripsi ni", R.drawable.ic_profile));
        posts.add(new Post("Ferdinand", "Aku cinta skripsiku", R.drawable.ic_profile));
        posts.add(new Post("El", "Gue blm siap ternyata buat mulai sm yg baru", R.drawable.ic_profile));
        posts.add(new Post("Esa", "Dont show your excitement ya, ren", R.drawable.ic_profile));

        return posts;
    }
}

