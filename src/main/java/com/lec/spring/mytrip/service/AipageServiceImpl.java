package com.lec.spring.mytrip.service;

import com.lec.spring.mytrip.domain.City;
import com.lec.spring.mytrip.domain.Question;
import com.lec.spring.mytrip.repository.CityRepository;
import com.lec.spring.mytrip.repository.UserCityRepository;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AipageServiceImpl implements AipageService {

    private final CityRepository cityRepository;
    private final UserCityRepository userCityRepository;

    public AipageServiceImpl(CityRepository cityRepository, UserCityRepository userCityRepository) {
        this.cityRepository = cityRepository;
        this.userCityRepository = userCityRepository;
    }

    @Override
    public List<Question> getQuestions() {
        List<Question> questions = new ArrayList<>();
        questions.add(new Question("1년에 한번 밖에 없는 휴가!", "계획적으로 여행 계획을 짜야지!", "느낌대로 가는게 여행의 묘미 아니겠어?!"));
        questions.add(new Question("나의 짐 챙기는 스타일은?", "여행가서 어떤 일이 생길지 몰라! 내가 우리 여행 바리바리 바리스타다!", "짐이 너무 많아도 문제야~ 필요한 것만 딱 챙겨가야지!"));
        questions.add(new Question("내 눈앞에 보였으면 하는 풍경은??", "답답한 도시를 벗어나~ 자연 풍경이 보여야 하지 않겠어?!", "다양한 문화를 품고 있는 멋진 도시 한번 구경해보고 싶어."));
        questions.add(new Question("사고 싶은 물건이 너무 많은데..?", "뭐가 문제야! 여행 왔는데 사고 싶은 거 다 사야지 FLEX~", "그래도 필요한 물건만 살 거야"));
        questions.add(new Question("외국인이 나에게 말을 건다..!!", "이번 기회에 나도 외국인 친구 만들어야지!", "어어... 도망가~!!!"));
        return questions;
    }

    @Override
    public City getRecommendedCity(List<String> answers) {
        if (answers.size() != 5) {
            throw new IllegalArgumentException("5개의 답변이 필요합니다.");
        }

        City city = cityRepository.findCityByAnswers(
                answers.get(0),
                answers.get(1),
                answers.get(2),
                answers.get(3),
                answers.get(4)
        );

        if (city == null) {
            throw new IllegalStateException("추천된 도시를 찾을 수 없습니다.");
        }

        return city;
    }

    @Override
    public void saveUserCityRecord(String userName, int cityId) {
        int userId = userCityRepository.findUserIdByName(userName);
        userCityRepository.insertUserCity(userId, cityId);
    }
}