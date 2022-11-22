package com.example.profru.MainScreen;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.profru.MainScreen.Study.StudyActivity;
import com.example.profru.MainScreen.Vacations.VacationActivity;
import com.example.profru.MainScreen.Vacations.VacationListActivity;
import com.example.profru.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager2 vp = findViewById(R.id.pager);
        ListPagerAdapter adapter = new ListPagerAdapter(this);
        adapter.addFragment(new VacationsSlide());
        adapter.addFragment(new StudySlide());
        vp.setAdapter(adapter);
    }

    private static class ListPagerAdapter extends FragmentStateAdapter {

        private ArrayList<Fragment> list;

        public ListPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);

            list = new ArrayList<>();
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
        public VacationsSlide() {

        }

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            super.onCreateView(inflater, container, savedInstanceState);

            View view = inflater.inflate(R.layout.activity_vacation_list, container);

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

            return view;
        }
    }

    public static class StudySlide extends Fragment {
        public StudySlide() {

        }

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            super.onCreateView(inflater, container, savedInstanceState);

            View view = inflater.inflate(R.layout.activity_study_list, container);

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

            return view;
        }
    }
}