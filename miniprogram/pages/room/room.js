// miniprogram/pages/room/room.js
const app = getApp()
const myurl = app.globalData.myurl
Page({

  /**
   * 页面的初始数据
   */
  data: {
    area: ['浮山校区', '金家岭校区','松山校区'],
    index: 0,
    start: ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11'],
    s_index: 0,
    end: ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11'],
    e_index: 0,
    results:[],
    hid:false,
    hidres:true,
    search_disabled:''
  },
  AreaChange: function (e) {
    this.setData({
      index: e.detail.value
    })
  },
  StartChange: function (e) {
    this.setData({
      s_index: e.detail.value
    })
  },
  EndChange: function (e) {
    this.setData({
      e_index: e.detail.value
    })
  },
  search:function(){
    var _this = this;
    var search_disabled = _this.data.search_disabled;
    this.setData({
      search_disabled:'true'
    });
    var area_index = _this.data.index;
    var start = parseInt(_this.data.s_index)+parseInt(1);
    var end = parseInt(_this.data.e_index) + parseInt(1);
    if(area_index==0){
      var area='fushan'
    }else if(area_index==1){
      var area = 'jinjialing'
    }else if(area_index==2){
      var area = 'songshan'
    }
    if(end>=start){
      wx.request({
        url: myurl + 'jw/classroom',
        header: {
          'content-type': 'application/json'
        },
        method: "post",
        data: {
          "cookie": wx.getStorageSync("successcookie"),
          "area": area,
          "start": start,
          "end": end
        },
        success: function (res) {
          console.log("sus");
          _this.setData({
            results: res.data.results,
            hid:true,
            hidres:false,
            search_disabled: ''
          })
          var res_length = _this.data.results.length;
          // console.log(res_length);
          if(res_length<=0){
            wx.showModal({
              content: '抱歉，当前时间没有空教室',
            })
          }
         
          
        },
        fail:function(){
          wx.showModal({
            title: '',
            content: '网络请求出错，请重试',
          })
        }
      })
    }else{
      wx.showModal({
        title: '输入有误',
        content: '开始结束需小于结束节数',
      })
      _this.setData({
        search_disabled: ''
      })
    }
    
  }, 
  gohomepage: function () {
    wx.redirectTo({
      url: '../homepage/homepage',
    })
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {

  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})