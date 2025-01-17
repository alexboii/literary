package com.example.alex.literary.mainactivity;




import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.*;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.services.books.Books;
import com.google.api.services.books.BooksRequestInitializer;
import com.google.api.services.books.Books.Volumes.List;
import com.google.api.services.books.model.Volume;
import com.google.api.services.books.model.Volumes;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.NumberFormat;

import com.example.alex.literary.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class BookProfile extends AppCompatActivity {

    public JSONArray bookArray;
    TextView tvTitle;
    TextView tvAuthor;
    WebView wbDescription;
    ImageView ivCover;
    public String bookTitle;
    ViewPager viewpager;
    FragmentPagerAdapter ft;

    BookProfile(String bookTitle){
        this.bookTitle = bookTitle;
    }

    BookProfile(){

    }


//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_book_profile, container, false);
//
////        MyActivity activity = (BookSwipe)getActivity();
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_book_profile);


        System.out.println(bookTitle);





        sampleCode(bookTitle);


    }
    public void displayBook(JSONArray array) throws JSONException {


        for (int i = 0; i < array.length(); i++) {

            myBook book = new myBook();
            JSONObject item = array.getJSONObject(i);

            JSONObject volumeInfo = item.getJSONObject("volumeInfo");
            String title = volumeInfo.getString("title");
            book.setBookTitle(title);
            System.out.println(book.getBookTitle());
            tvTitle.setText(book.getBookTitle());

            JSONArray authors = volumeInfo.getJSONArray("authors");
            String author = authors.getString(0);
            book.setBookAuthor(author);
            System.out.println(book.getBookAuthor());
            tvAuthor.setText(book.getBookAuthor());


            if(volumeInfo.has("imageLinks")) {
                JSONObject imageLinks = volumeInfo.getJSONObject("imageLinks");
                String imageLink = imageLinks.getString("thumbnail");
                book.setBookImageURL(imageLink);
                System.out.println(book.getBookImageURL());

                URL url = null;
                try {
                    url = new URL(book.getBookImageURL());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                Bitmap bmp = null;
                try {
                    bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ivCover.setImageBitmap(bmp);
            }
            else{
                book.setBookImageURL("No URL");
            }

        }

        }


    public void sampleCode(String newText){

        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvAuthor = (TextView) findViewById(R.id.tvAuthor);
        ivCover = (ImageView) findViewById(R.id.ivCover);
        wbDescription = (WebView) findViewById(R.id.wbDescription);
        wbDescription.setBackgroundColor(Color.TRANSPARENT);


        if(newText.length()>0)
        {
            final String copyNewText = newText;
            newText = newText.replace(" ", "+");
            newText = newText.replace("-", "");
            String url = "https://www.googleapis.com/books/v1/volumes?q=";
            url = url + newText;

            AsyncHttpClient client = new AsyncHttpClient();
            final String finalNewText = newText;
            client.get(url, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
//                    java.util.List<myBook> bookList = new ArrayList<myBook>();

                    String json = new String(responseBody);

                    try {
                        JSONObject object = new JSONObject(json);
                        JSONArray array = object.getJSONArray("items");

                        for (int i = 0; i < array.length(); i++) {

                            myBook book = new myBook();
                            JSONObject item = array.getJSONObject(i);

                            JSONObject volumeInfo = item.getJSONObject("volumeInfo");

                            if(volumeInfo.has("title") && volumeInfo.getString("title").equals(copyNewText) && volumeInfo.has("authors") && volumeInfo.has("description") && volumeInfo.has("imageLinks")){

                                System.out.println("Am I here?");

                                String title = volumeInfo.getString("title");
                                book.setBookTitle(title);
                                if(title.equals(copyNewText)){
                                System.out.println(book.getBookTitle());
                                tvTitle.setText(book.getBookTitle());}
                                else{
                                    tvTitle.setText(copyNewText);
                                }

                                JSONArray authors = volumeInfo.getJSONArray("authors");
                                String author = authors.getString(0);
                                book.setBookAuthor(author);
                                System.out.println(book.getBookAuthor());
                                tvAuthor.setText("Author: " + book.getBookAuthor());

                                String description = volumeInfo.getString("description");
                                System.out.println(description);

                                String text;
                                text = "<html><body><p align=\"justify\">";
                                text+= description;
                                text+= "</p></body></html>";
                                wbDescription.loadData(text, "text/html", "utf-8");


                                    JSONObject imageLinks = volumeInfo.getJSONObject("imageLinks");
                                    String imageLink = imageLinks.getString("thumbnail");
                                    book.setBookImageURL(imageLink);
                                    System.out.println(book.getBookImageURL());

                                    URL url = null;
                                    try {
                                        url = new URL(book.getBookImageURL());
                                    } catch (MalformedURLException e) {
                                        e.printStackTrace();
                                    }
                                    Bitmap bmp = null;
                                    try {
                                        bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    ivCover.setImageBitmap(bmp);


                                break;
                            }else {
                                String title = volumeInfo.getString("title");
                                book.setBookTitle(title);
                                System.out.println(book.getBookTitle());
                                tvTitle.setText(book.getBookTitle());

                                JSONArray authors = volumeInfo.getJSONArray("authors");
                                String author = authors.getString(0);
                                book.setBookAuthor(author);
                                System.out.println(book.getBookAuthor());
                                tvAuthor.setText("Author: " + book.getBookAuthor());


                                if (volumeInfo.has("imageLinks")) {
                                    JSONObject imageLinks = volumeInfo.getJSONObject("imageLinks");
                                    String imageLink = imageLinks.getString("thumbnail");
                                    book.setBookImageURL(imageLink);
                                    System.out.println(book.getBookImageURL());

                                    URL url = null;
                                    try {
                                        url = new URL(book.getBookImageURL());
                                    } catch (MalformedURLException e) {
                                        e.printStackTrace();
                                    }
                                    Bitmap bmp = null;
                                    try {
                                        bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    ivCover.setImageBitmap(bmp);
                                } else {
                                    book.setBookImageURL("No URL");
                                }
                            }

                        }
//
//                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

//                    LibraryFragment.showBooksList(getApplicationContext(), bookList);
                }

                @Override
                public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

                }

            });
        }


    }
}



