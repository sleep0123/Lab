const today = new Date();
const months = ["1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"];
const days = ["日", "月", "火", "水", "木", "金", "土"];
const trS = '<tr>';
const trE = '</tr>';
const tdS = '<td>';
const tdE = '</td>';

// 選択可能な年を作成
$(function () {
  let validYears = "";
  for (let year = today.getFullYear() - 100; year < today.getFullYear() + 100; year++) {
    validYears += "<option value='" + year + "'>" + year + "</option>";
  }
  
  $('#selectedYear').html(validYears);
  $('#selectedYear').val(today.getFullYear());
});

// 選択可能な月を作成
$(function () {
  let validMonths = "";
  months.forEach((elem, index) => {
    validMonths += "<option value='" + index + "'>" + elem + "</option>";
  });

  $('#selectedMonth').html(validMonths);
  $('#selectedMonth').val(today.getMonth());
});

// カレンダーのヘッダー作成
function makeCalendarHeader() {

  let calendarHeader = trS;
  for (day of days) {
    calendarHeader += "<th data-day='" + day + "'>" + day + "</th>";
  }

  calendarHeader += trE;

  $(".calendar-header").html(calendarHeader);
};

// カレンダー作成
function makeCalendar(year, month) {
  
  $('.calendar-body').empty();
//  const selectedYear = $('#selectedYear').val();
//  const selectedMonth = $('#selectedMonth').val();
//  var firstDay = new Date(year, month).getDay();
//  var endDate = new Date(year, month + 1, 0).getDate();
//  var sumFirstDayAndEndDate = firstDay + endDate;

//  const numWeek = sumFirstDayAndEndDate % 7 == 0 ? sumFirstDayAndEndDate / 7 : sumFirstDayAndEndDate / 7 + 1;
  var $calendarBody = $('.calendar-body');
  
//  for(var k = 0; k < $calendarBody.length; k++)

  $.each($calendarBody, function () {
    switch($(this).data('value')){
        case -1:
          var afterMonth = month != 0 ? month - 1 : 11;
          var afterYear = month != 11 ? year : year - 1;
          break;
        
        case 0:
          var afterMonth = month;
          var afterYear = year;
          break;
          
        case 1:
          var afterMonth = month != 11 ? month + 1 : 0;
          var afterYear = month != 0 ? year : year+ 1;
          break; 
    }
    
    var firstDay = new Date(afterYear, afterMonth).getDay();
    var endDate = new Date(afterYear, afterMonth + 1, 0).getDate();
    var sumFirstDayAndEndDate = firstDay + endDate;
    
    var numWeek = sumFirstDayAndEndDate % 7 == 0 ? sumFirstDayAndEndDate / 7 : sumFirstDayAndEndDate / 7 + 1;
    
    var date = 1;
    for(var i = 0; i < numWeek; i++){
      var line = trS;
      for(var j = 0; j < 7; j++){
        if (i === 0 && j < firstDay) {
          line += tdS + tdE;
          continue;
        } else if (date > 31){
          break;
        }
        line += tdS + `<a data-date='${afterYear}/${afterMonth+1}/${date}' href='/diary?year=${afterYear}&month=${afterMonth}&date=${date}'>`+ date + '</a>' + tdE;
        date++;
      }
      line += trE
      $(this).append(line);
    }
  });
};

// 年月を返す
function getMonthYear() {
  $('#monthYear').text(months[$('#selectedMonth').val()] + ' ' + $('#selectedYear').val());
}

function showCalendar(year, month) {
    getMonthYear();
    makeCalendarHeader();
    makeCalendar(year, month);
    check();
};

$(function () {
  const selectedYear = parseInt($('#selectedYear').val());
  const selectedMonth = parseInt($('#selectedMonth').val());
  getMonthYear();
  showCalendar(selectedYear, selectedMonth); 
  check();
});


// 日記がつけられている日
function check() {
  var recorded = [];
  $('.recorded').each(function () {
    recorded.push($(this).val());
  });
  $('.calendar-body a').each(function () {
    for(var record in recorded){
      if ($(this).data('date') == recorded[record]) {
        $(this).addClass('uk-text-emphasis');
        break;
      }
    }
  });
};


// 一月前に戻る
$(function () {
  $('#previous').click(function () {
    let year = $('#selectedYear').val();
    let month = $('#selectedMonth').val() != 0 ? $('#selectedMonth').val()-1 : 11;
    
    if (month == 11) {
        year -=1;
    }
    $('#selectedYear').val(parseInt(year));
    $('#selectedMonth').val(parseInt(month));
    showCalendar(year, month)
  });

// 一月先にいく
    $('#next').click(function () {
      let year = parseInt($('#selectedYear').val());
      let month = $('#selectedMonth').val() != 11 ? parseInt($('#selectedMonth').val())+1 : 0;
    
      if (month == 0) {
        year += 1;
      }
      
      $('#selectedYear').val(parseInt(year));
      $('#selectedMonth').val(parseInt(month));
      showCalendar(year, month)
    });

// 選択された月にいく
    $('#selectedMonth, #selectedYear').change(function () {
        showCalendar($('#selectedYear').val(), $('#selectedMonth').val());
    });
});
