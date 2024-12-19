// audio.js

document.addEventListener("DOMContentLoaded", function() {
    var bgMusic = document.getElementById('bgMusic');

    if (bgMusic) {
        // 오디오 상태 로컬 스토리지에서 가져오기
        function checkAudioStatus() {
            const isPlaying = localStorage.getItem('isAudioPlaying');
            if (isPlaying === 'true') {
                bgMusic.play();
            } else {
                bgMusic.pause();
            }
        }

        // 오디오 상태 로컬 스토리지에 저장
        function setAudioStatus(isPlaying) {
            localStorage.setItem('isAudioPlaying', isPlaying);
        }

        // 오디오 재생/일시정지 토글 함수
        function toggleAudio() {
            if (bgMusic.paused) {
                bgMusic.play();
                setAudioStatus(true);
            } else {
                bgMusic.pause();
                setAudioStatus(false);
            }
        }

        // 페이지 로드 시 오디오 상태 확인
        checkAudioStatus();

        // 버튼 클릭 시 토글
        document.getElementById('toggleAudio').addEventListener('click', toggleAudio);
    } else {
        console.error('Audio element not found!');
    }
});
