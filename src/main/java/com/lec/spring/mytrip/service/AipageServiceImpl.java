package com.lec.spring.mytrip.service;

import com.lec.spring.mytrip.controller.AipageController;
import com.lec.spring.mytrip.domain.City;
import com.lec.spring.mytrip.domain.Question;
import com.lec.spring.mytrip.repository.AipageRepository;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AipageServiceImpl implements AipageService{

//
//    private final AipageRepository aipageRepository;
//
//    private final List<String> answers = new ArrayList<>();
//
//    public AipageServiceImpl(SqlSession sqlSession) {
//        aipageRepository = sqlSession.getMapper(AipageRepository.class);
//    }
//
//
//    // 질문 데이터 제공
//    @Override
//    public List<Question> getQuestions() {
//        List<Question> questions = new ArrayList<>();
//        questions.add(new Question("1. 1년에 한번 밖에 없는 휴가!", "계획적으로 여행 계획을 짜야지! ", "느낌대로 가는게 여행의 묘미 아니겠어?!"));
//        questions.add(new Question("2. 나의 짐 챙기는 스타일은?", "여행가서 어떤 일이 생길지 몰라! 내가 우리여행 바리바리 바리스타다!", "짐이 너무 많아도 문제야~ 필요한것만 딱 챙겨가야지!"));
//        questions.add(new Question("3. 내 눈앞에 보였으면 하는 풍경은??", "답답한 도시를 벗어나~ 자연풍경이 보여야하지 않겠어?!", "다양한 문화를 품고있는 멋쟁이 도시 한번 구경해보고싶어 "));
//        questions.add(new Question("4. 사고 싶은 물건이 너무 많은데..?", "뭐가 문제야! 여행왔는데 사고싶은거 다 사야지 FLEX~", "그래도 필요한 물건만 살꺼야"));
//        questions.add(new Question("5. 외국인이 나에게 말을 건다..!!", "이번 기회에 나도 외국인 친구 만들어야지!", "어어...도망가~!!!"));
//        return questions;
//    }
//
//    // 사용자 답변 저장
//    @Override
//    public void saveAnswers(List<String> userAnswers) {
//        answers.clear();
//        answers.addAll(userAnswers);
//    }
//
//    // 추천 도시 반환
//    @Override
//    public City getRecommendedCity() {
//        if (answers.size() != 5) {
//            throw new IllegalStateException("모든 질문에 대한 답변이 필요합니다!");
//        }
//        return aipageRepository.findCityByAnswers(
//                answers.get(0),
//                answers.get(1),
//                answers.get(2),
//                answers.get(3),
//                answers.get(4)
//        );
//    }
//
//
//    // 사용자 이름 반환
//    @Override
//    public String getUserName() {
//        return "";
//    }

}
