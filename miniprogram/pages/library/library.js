// miniprogram/pages/library/library.js
const app = getApp()
const myurl = app.globalData.myurl
Page({
  data: {
    hid_page: true,
    hid_prior: true,
    hid_next: true,
    hid:false,
    hid2:false,
    inputValue:'',
    type:['书名','作者'],
    index:0,
    results:[],
    count:'',
    page:1
  },
  TypeChange:function(e){
    this.setData({
      index:e.detail.value
    })
  },
  search:function(e){
    var _this=this;
    var inputValue = _this.data.inputValue;
    var typeName = _this.data.type[this.data.index];
    var index=_this.data.index;
    var type='';
    var page = _this.data.page;
    
    // 转换类型name or author
    if(typeName.indexOf('书名')>=0){
      type='name'
    }else{
      type='author'
    }
    if(!inputValue){
      wx.showModal({
        title: '请重试',
        content: '输入不得为空',
      })
    }else{
      wx.request({
        url: myurl + 'library/search',
        header: {
          'content-type': 'application/json'
        },
        method: "post",
        data: {
          "type": type,
          "text": inputValue,
          "page": page
        },
        success: function (res) {
          if (res.data.count > 0) {
            _this.setData({
              results: res.data.results,
              count: res.data.count,
              // page:page,          
              hid_page: false,
              hid: true,
              hid2: true
            })
          } else {
            wx.showModal({
              title: '查询失败',
              content: '没有查询到该书',
            })
          }


          var count = res.data.count;
          // console.log('count='+count)
          // console.log('page'+page)
          // 剩余页数
          var restpage = count - page;

          // console.log('restpage'+restpage)
          if (restpage > 0) { //剩余页数>0，还可以点击下一页
            _this.setData({
              hid_next: false
            })
          } else {
            _this.setData({
              hid_next: true
            })
          }
          if (page > 1) { //当前页数>1，还可以点击上一页
            _this.setData({
              hid_prior: false
            })
          } else {
            _this.setData({
              hid_prior: true
            })
          }
        },
        fail: function (res) {
          wx.showModal({
            content: '服务器开小差了',
          })
        }
      })
    }
    
    // var count = _this.data.count;
   
  },
  next:function(){
    var that=this
    var nowpage=that.data.page;
    var nextpage=nowpage+1;
    that.setData({
      page:nextpage,     
    })
    that.search();
  },
  prior: function () {
    var that = this
    var nowpage = that.data.page;
    console.log('nowpage' + nowpage)
    var priorpage = nowpage -1;
    console.log('priorpage' + priorpage)
    that.setData({
      page: priorpage,
      
    })
    that.search();
  },
  godetail:function(e){
    var id = e.currentTarget.dataset.id;
    wx.setStorageSync("id", id);
    wx.navigateTo({
      url: '../library/detail/detail',
    })
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    
  },

  bindKeyInput:function(e){
    var inputValue= e.detail.value;
    this.setData({
      inputValue:inputValue,
      page:1
    })
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
