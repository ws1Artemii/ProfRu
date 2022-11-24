package com.example.profru.MainScreen;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.profru.BuildConfig;
import com.example.profru.MainScreen.Study.StudyActivity;
import com.example.profru.MainScreen.Vacations.VacationActivity;
import com.example.profru.MainScreen.Vacations.VacationListActivity;
import com.example.profru.R;
import com.example.profru.RegistrationAndSignIn.SignInActivity;
import com.example.profru.Resume.ResumeActivity;
import com.example.profru.User;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private static User user;
    private static Drawable avatar_def;
    private ListPagerAdapter adapter;
    private ViewPager2 vp;
    private View load;

    private static int RESULT_RESUME = 10;
    private static int RESULT_AVATAR = 1541970047;

    private Activity act = MainActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user = new User();
        try {
            user.ParseXml();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }

        avatar_def = getDrawable(R.drawable.avatar_image);

        Log.e("USER", user.toString());

        vp = findViewById(R.id.pager);
        adapter = new ListPagerAdapter(this);

        load = findViewById(R.id.load);

        AsyncAdd add = new AsyncAdd();
        add.execute();
    }

    private class AsyncAdd extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... strings) {

            adapter.addFragment(new VacationsSlide(getLayoutInflater()));
            adapter.addFragment(new StudySlide(getLayoutInflater()));
            adapter.addFragment(new ProfileSlide(getLayoutInflater()));

            return "Complete";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            vp.setAdapter(adapter);
            AlphaAnimation a = new AlphaAnimation(1.0f, 0.0f);
            a.setFillAfter(true);
            a.setDuration(500);
            load.setVisibility(View.INVISIBLE);
            load.startAnimation(a);
        }
    }

    private static class ListPagerAdapter extends FragmentStateAdapter {

        private ArrayList<Fragment> list;

        public ListPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);

            list = new ArrayList<>();
        }

        public void reset(int position) {
            ListPagerAdapter.this.notifyItemChanged(position);
        }

        public void addFragment(Fragment f) {
            list.add(f);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return list.get(position);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    public static class VacationsSlide extends Fragment {

        private View view;

        public VacationsSlide(LayoutInflater inflater) {
            view = inflater.inflate(R.layout.activity_vacation_list, null, false);

            ((TextView) view.findViewById(R.id.fio)).setText(user.fullname);

            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yy");

            int year = Calendar.getInstance().get(Calendar.YEAR);
            int month = Calendar.getInstance().get(Calendar.MONTH);
            int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

            Date today = new Date(year, month, day);
            try {
                Date birth = sdf.parse(user.birthdate);

                int years = today.getYear() - birth.getYear() - 1900;
                if((birth.getMonth() > today.getMonth()) || (birth.getMonth() == today.getMonth() && birth.getTime() > today.getTime()))
                    years--;

                char c = String.valueOf(years).charAt(String.valueOf(years).length() - 2);
                if(c == '1'
                        || c == '5'
                        || c == '6'
                        || c == '7'
                        || c == '8'
                        || c == '9'
                        || c == '0')
                    ((TextView) view.findViewById(R.id.age)).setText(years + " лет");
                else {
                    char c1 = String.valueOf(years).charAt(String.valueOf(years).length() - 1);
                    if( c1 == '1')
                        ((TextView) view.findViewById(R.id.age)).setText(years + " год");
                    else if( c1 == '2'
                            || c == '3'
                            || c == '4')
                        ((TextView) view.findViewById(R.id.age)).setText(years + " года");
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }

            if(user.avatar_path == null)
                ((ImageView) view.findViewById(R.id.avatar)).setImageDrawable(avatar_def);
            else
                ((ImageView) view.findViewById(R.id.avatar)).setImageBitmap(BitmapFactory.decodeFile(user.avatar_path));

            view.findViewById(R.id.card1).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getActivity(), VacationActivity.class);
                    i.putExtra("title", "Junior Web-разработчик / инженер-программист (ASP.NET, C#.net, JS, Oracle)");
                    i.putExtra("description", "Команда разработчиков компании ООО «НПК «Прогресс» занимается развитием и сопровождением комплексной автоматизированной системы для территориального фонда обязательного медицинского страхования Республики Башкортостан, а также для страховых медицинских организаций Республики Башкортостан.\n" +
                            "\n\n" +
                            "Для доработки и создания новых подсистем приложения ищем разработчика, способного решать нестандартные задачи.");
                    i.putExtra("image", 1);
                    i.putExtra("zp", 50000);
                    i.putExtra("actions", "доработка уже существующих и создание новых модулей Web приложения (ASP.NET, C#.net, JS, Oracle).");
                    startActivity(i);
                }
            });

            view.findViewById(R.id.card2).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getActivity(), VacationActivity.class);
                    i.putExtra("title", "Инженер по обслуживанию компьютерной техники");
                    i.putExtra("description", "Федеральная компания по обслуживанию банковского оборудования приглашает на работу инженера по ремонту компьютерной техники.\n" +
                            "\n" +
                            "Требования:\n" +
                            "Л/А\n" +
                            "Техническая грамотность\n" +
                            "Ответственность\n\n" +
                            "Условия:\n" +
                            "Свободный график, возможность совмещения\n" +
                            "Компенсация ГСМ и сотовой связи\n" +
                            "Возможность карьерного роста");
                    i.putExtra("image", 2);
                    i.putExtra("zp", 30000);
                    i.putExtra("actions", "Диагностика оборудования, замена вышедших из строя блоков");
                    startActivity(i);
                }
            });
        }

        public void reset() {
            ((TextView) view.findViewById(R.id.fio)).setText(user.fullname);

            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yy");

            int year = Calendar.getInstance().get(Calendar.YEAR);
            int month = Calendar.getInstance().get(Calendar.MONTH);
            int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

            Date today = new Date(year, month, day);
            try {
                Date birth = sdf.parse(user.birthdate);

                int years = today.getYear() - birth.getYear() - 1900;
                if((birth.getMonth() > today.getMonth()) || (birth.getMonth() == today.getMonth() && birth.getTime() > today.getTime()))
                    years--;

                char c = String.valueOf(years).charAt(String.valueOf(years).length() - 2);
                if(c == '1'
                        || c == '5'
                        || c == '6'
                        || c == '7'
                        || c == '8'
                        || c == '9'
                        || c == '0')
                    ((TextView) view.findViewById(R.id.age)).setText(years + " лет");
                else {
                    char c1 = String.valueOf(years).charAt(String.valueOf(years).length() - 1);
                    if( c1 == '1')
                        ((TextView) view.findViewById(R.id.age)).setText(years + " год");
                    else if( c1 == '2'
                            || c == '3'
                            || c == '4')
                        ((TextView) view.findViewById(R.id.age)).setText(years + " года");
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }

            if(user.avatar_path == null)
                ((ImageView) view.findViewById(R.id.avatar)).setImageDrawable(avatar_def);
            else
                ((ImageView) view.findViewById(R.id.avatar)).setImageBitmap(BitmapFactory.decodeFile(user.avatar_path));
        }

        public void changeAvatarImage() {
            if(user.avatar_path == null)
                ((ImageView) view.findViewById(R.id.avatar)).setImageDrawable(avatar_def);
            else
                ((ImageView) view.findViewById(R.id.avatar)).setImageBitmap(BitmapFactory.decodeFile(user.avatar_path));
        }

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            super.onCreateView(inflater, container, savedInstanceState);

            return view;
        }
    }

    public static class StudySlide extends Fragment {

        private View view;

        public StudySlide(LayoutInflater inflater) {
            view = inflater.inflate(R.layout.activity_study_list, null, false);

            view.findViewById(R.id.card1).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getActivity(), StudyActivity.class);
                    i.putExtra("title", "Курсы по разработке веб и мильтимедийных приложений");
                    i.putExtra("description", "Вы научитесь разрабатывать XML разметку и UI андроид приложений" +
                            "\n\n" + "Вы научитесь разрабатывать XML разметку и UI андроид приложений"
                            + "\n\n" + "Вы научитесь работать с аудио, видео и изображениями");
                    i.putExtra("image", 3);
                    i.putExtra("cost", 22000);
                    i.putExtra("time", 2);
                    startActivity(i);
                }
            });

            view.findViewById(R.id.card2).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getActivity(), StudyActivity.class);
                    i.putExtra("title", "Обучение системному администрированию");
                    i.putExtra("description", "До того, как были изобретены сетевые технологии, все компьютеры работали разрозненно. Но после того как количество персональных компьютеров увеличилось, возникла необходимость создания общей рабочей среды. В то же время появилась потребность обеспечения управления различными рабочими процессами, а также реализации разнообразных задач. Именно такие функции возложены на администрирование компьютерных сетей.");
                    i.putExtra("image", 4);
                    i.putExtra("cost", 43000);
                    i.putExtra("time", 5);
                    startActivity(i);
                }
            });
        }

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            super.onCreateView(inflater, container, savedInstanceState);

            return view;
        }
    }

    public static class ProfileSlide extends Fragment {

        private View view;

        public ProfileSlide(LayoutInflater inflater) {
            view = inflater.inflate(R.layout.activity_profile, null, false);

            if(user.avatar_path == null)
                ((ImageView) view.findViewById(R.id.avatar)).setImageDrawable(avatar_def);
            else
                ((ImageView) view.findViewById(R.id.avatar)).setImageBitmap(BitmapFactory.decodeFile(user.avatar_path));

            view.findViewById(R.id.editresume).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getActivity(), ResumeActivity.class);
                    i.putExtra("edit", true);
                    startActivity(i);
                    getActivity().finish();
                }
            });

            view.findViewById(R.id.logout).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getActivity(), SignInActivity.class));
                    getActivity().finish();
                }
            });

            view.findViewById(R.id.editprofile).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        getActivity().startActivityForResult(i, RESULT_RESUME);
                    }
                }
            });
        }

        public void changeAvatarImage() {
            if(user.avatar_path == null)
                ((ImageView) view.findViewById(R.id.avatar)).setImageDrawable(avatar_def);
            else
                ((ImageView) view.findViewById(R.id.avatar)).setImageBitmap(BitmapFactory.decodeFile(user.avatar_path));
        }

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            super.onCreateView(inflater, container, savedInstanceState);

            return view;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.e("Req code", requestCode + "");

        if ((requestCode == RESULT_RESUME) && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            user.avatar_path = cursor.getString(columnIndex);
            cursor.close();

            AlphaAnimation a = new AlphaAnimation(0.0f, 1.0f);
            a.setFillAfter(true);
            a.setDuration(500);
            load.setVisibility(View.VISIBLE);
            load.startAnimation(a);
            Log.e("Image", "Recieved");

            new AsyncReinportUser().execute();
        }
    }

    void Restart() {
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
            act.recreate();
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }
        else {
            Intent i = getIntent();
            act.finish();
            startActivity(i);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }
    }

    private class AsyncReinportUser extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... strings) {

            Log.e("Async", "Started");

            try {
                user.Save();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return "Complete";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Restart();
                }
            }, 600);
        }
    }
}