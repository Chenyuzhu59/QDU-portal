// miniprogram/pages/timetable/detail.js
Page({
  data: {
    coursename:'',
    teacher:'',
    credit:'',
    assess:'',
    timeAndPlace:[]
  },
  onLoad: function (options) {
    var that =this
    var detail_classid = wx.getStorageSync("detail_classid");
    var totaldata = wx.getStorageSync("timetabledata");

    var coursename, teacher, credit, assess,timeAndPlace;
    for(var i=0;i<totaldata.length ; i++){
      if (i == detail_classid){
        coursename= totaldata[i].name;
        teacher = totaldata[i].teacher;
        credit = totaldata[i].credit;
        assess = totaldata[i].assess;
        
        timeAndPlace = totaldata[i].timeAndPlace;
        for (var j = 0; j < timeAndPlace.length ;j++){
          var weeks = timeAndPlace[j].weeks;
          var weekday = timeAndPlace[j].weekday;
          var start = timeAndPlace[j].start;
          var end = timeAndPlace[j].end;
          var place = timeAndPlace[j].place;
          var tmp = weeks[0] + "-" + weeks[weeks.length-1] +" 星期"+weekday + " 第"+start + "-" +end+"节";
          that.data.timeAndPlace.push(tmp);
        }
        break;
      }
    }
    that.setData({
      timeAndPlace: that.data.timeAndPlace,
      coursename: coursename,
      teacher: teacher,
      credit: credit,
      assess: assess
    })
  }
})