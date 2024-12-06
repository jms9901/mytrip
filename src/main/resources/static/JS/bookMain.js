function loadApp() {

	// Create the flipbook

	$('.flipbook').turn({
		// Width

		width:922,

		// Height

		height:600,

		// Elevation

		elevation: 50,

		// Enable gradients

		gradients: true,

		// Auto center this flipbook

		autoCenter: true

	});
	$('.flipbook').bind('turning', function(event, page) {
		if (page === 2) {
			$('.menu1').show(); // Show menu after the first page turn
			$('.menu2').show();
			$('.menu3').show();
			$('.menu4').show();
			$('.menu5').show();
		}else if (page ===1){
			$('.menu1').hide();
			$('.menu2').hide();
			$('.menu3').hide();
			$('.menu4').hide();
			$('.menu5').hide();
		}
	});
	$('.goChat').hover(function(){
		hoverChat();
	});

	function hoverChat() {
		let angle = 0;
		clearInterval(shakeInterval);
		$('.goChat').css('transform', 'rotateY(0deg)');  // 초기 상태 설정
		let shakeInterval = setInterval(function() {
			angle += 10;  // 한 번에 10도씩 증가
			$('.goChat').css('transform', `rotateY(${angle}deg)`);  // Y축을 중심으로 회전
			if (angle >= 35) {  // 35도까지 회전
				clearInterval(shakeInterval);
				$('.goChat').css('transform', 'rotateY(0deg)');  // 원래 위치로 돌아가게 설정
			}
		}, 100);  // 0.1초마다 회전
	}

}

$('.myPageUpdate').click(function (){
	$('.updateUserModal').show();
});
$('.closeUpdateModal').click(function(){
	$('.updateUserModal').hide();
});



// Load the HTML4 version if there's not CSS transform

function isCSSTransformSupported() {
	var testElement = document.createElement('div');
	var transformProperties = ['transform', 'WebkitTransform', 'MozTransform', 'OTransform', 'msTransform'];

	for (var i = 0; i < transformProperties.length; i++) {
		if (testElement.style[transformProperties[i]] !== undefined) {
			return true; // CSS transform이 지원됩니다.
		}
	}

	return false; // CSS transform이 지원되지 않습니다.
}

if (isCSSTransformSupported()) {
	// transform 지원 시
	$.getScript('../../lib/animationjs/turn.js', function() {
		loadApp();
	});
} else {
	// transform 미지원 시
	$.getScript('../../lib/JQuery/turn.html4.min.js', function() {
		loadApp();
	});
}



function addPage(page, book) {

	var id, pages = book.turn('pages');

	// Create a new element for this page
	var element = $('<div />', {});

	// Add the page to the flipbook
	if (book.turn('addPage', element, page)) {

		// Add the initial HTML
		// It will contain a loader indicator and a gradient
		element.html('<div class="gradient"></div><div class="loader"></div>');

		// Load the page
		loadPage(page, element);
	}

}

function loadPage(page, pageElement) {

	// Create an image element

	var img = $('<img />');

	img.mousedown(function(e) {
		e.preventDefault();
	});

	img.load(function() {
		
		// Set the size
		$(this).css({width: '100%', height: '100%'});

		// Add the image to the page after loaded

		$(this).appendTo(pageElement);

		// Remove the loader indicator
		
		pageElement.find('.loader').remove();
	});

	// Load the page

	img.attr('src', 'pages/' +  page + '.jpg');

}


function loadLargePage(page, pageElement) {
	
	var img = $('<img />');

	img.load(function() {

		var prevImg = pageElement.find('img');
		$(this).css({width: '100%', height: '100%'});
		$(this).appendTo(pageElement);
		prevImg.remove();
		
	});

	// Loadnew page
	
	img.attr('src', 'pages/' +  page + '-large.jpg');
}


function loadSmallPage(page, pageElement) {
	
	var img = pageElement.find('img');

	img.css({width: '100%', height: '100%'});

	img.unbind('load');
	// Loadnew page

	img.attr('src', 'pages/' +  page + '.jpg');
}



// http://code.google.com/p/chromium/issues/detail?id=128488
function isChrome() {

	return navigator.userAgent.indexOf('Chrome')!=-1;

}

