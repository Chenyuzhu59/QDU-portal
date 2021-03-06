
Page({

  /**
   * 页面的初始数据
   */
  data: {
    text:'\n',
    oname: [{
      "name": "教务处",
      "arr": [{
        "name": "教务处处长",
        "tele": "0532-85951877"
      },
      {
        "name": "教务处副处长",
        "tele": "0532-85953715"
      },
      {
        "name": "教务处副处长",
        "tele": "0532-85950028"
      }]
    },
    {
      "name": "招生办",
      "arr": [{
        "name": "马克思主义学院",
        "tele": "0532-85950935"
      },
      {
        "name": "文学院",
        "tele": "0532-85953352"
      },
      {
        "name": "哲学与历史学院",
        "tele": "0532-85955278"
      },
      {
        "name": "新闻与传播学院",
        "tele": "0532-85953030"
      },
      {
        "name": "外语学院",
        "tele": "0532-85953379"
      },
      {
        "name": "体育学院",
        "tele": "0532-85951430"
      },
      {
        "name": "音乐学院",
        "tele": "0532-85953738"
      },
      {
        "name": "美术学院",
        "tele": "0532-85953828"
      },
      {
        "name": "数学与统计学院",
        "tele": "0532-85953660"
      },
      {
        "name": "物理科学学院",
        "tele": "0532-85957290"
      },
      {
        "name": "生命科学学院",
        "tele": "0532-85959690"
      },
      {
        "name": "师范学院（教师教育学院）",
        "tele": "0532-85957086"
      },
      {
        "name": "经济学院",
        "tele": "0532-85958737"
      },
      {
        "name": "法学院",
        "tele": "0532-85955971"
      },
      {
        "name": "政治与公共管理学院",
        "tele": "0532-85953523"
      },
      {
        "name": "商学院",
        "tele": "0532-85958017"
      },
      {
        "name": "旅游与地理科学学院",
        "tele": "0532-85952031"
      },
      {
        "name": "化学化工学院",
        "tele": "0532-85951290"
      },
      {
        "name": "材料科学与工程学院",
        "tele": "0532-85953982"
      },
      {
        "name": "环境科学与工程学院",
        "tele": "0532-85953966"
      },
      {
        "name": "机电工程学院",
        "tele": "0532-85953626"
      },
      {
        "name": "自动化学院",
        "tele": "0532-85953780"
      },
      {
        "name": "电子信息学院",
        "tele": "0532-85950303"
      },
      {
        "name": "计算机科学技术学院",
        "tele": "0532-85955812"
      },
      {
        "name": "数据科学与软件工程学院",
        "tele": "0532-85953366"
      },
      {
        "name": "纺织服装学院",
        "tele": "0532-85953038"
      },
      {
        "name": "电气工程学院",
        "tele": "0532-85951981"
      },
      {
        "name": "医学部",
        "tele": "0532-82991011"
      }]
    },
    {
      "name": "财务处",
      "arr": [{
        "name": "综合信息中心",
        "tele": "0532-85952119"
      },
      {
        "name": "预算管理中心",
        "tele": "0532-85953951"
      },
      {
        "name": "资金管理中心",
        "tele": "0532-85953950"
      },
      {
        "name": "收费管理中心",
        "tele": "0532-85953976"
      },
      {
        "name": "科研经费管理中心",
        "tele": "0532-85953972 "
      },
      {
        "name": "稽核中心",
        "tele": "0532-85957115"
      },
      {
        "name": "会计委派中心",
        "tele": "0532-85952419 "
      },
      {
        "name": "浮山校区核算部",
        "tele": "0532-85955890 "
      },
      {
        "name": "金家岭校区核算部",
        "tele": "0532-85959892 "
      },
      {
        "name": "松山校区核算部",
        "tele": "0532-82991023 "
      },
      {
        "name": "基本建设核算部",
        "tele": "0532-85952604"
      }]
    },
    {
      "name": "浮山校区常用电话",
      "arr": [{
        "name": "校总值班室",
        "tele": "0532-85952502"
      },
      {
        "name": "保卫处值班电话（24小时）",
        "tele": "0532-85951110"
      },
      {
        "name": "校医院值班室（24小时）",
        "tele": "0532-85955120"
      },
      {
        "name": "电话故障报修",
        "tele": "0532-85951112"
      },
      {
        "name": "校长公开电话",
        "tele": "0532-85951111"
      },
      {
        "name": "网络中心",
        "tele": "0532-85955990 "
      },
      {
        "name": "国际交流服务",
        "tele": "0532-85951680"
      },
      {
        "name": "图书馆咨询电话",
        "tele": "0532-85954239 "
      },
      {
        "name": "学生处（办公室）",
        "tele": "0532-85953126 "
      },
      {
        "name": "学生处（学生公寓管理中心）",
        "tele": "0532-85953265 "
      },
      {
        "name": "后勤处（投诉建议）",
        "tele": "0532-85952315 "
      },
      {
        "name": "后勤处（物业维修）",
        "tele": "0532-85951498"
      },
      {
        "name": "后勤处（水电维修）",
        "tele": "0532-85951747 "
      },
      {
        "name": "后勤处（暖气维修）",
        "tele": "0532-85951363 "
      }]
    },
    {
      "name": "金家岭校区常用电话",
      "arr": [{
        "name": "动力中心（水电暖服务）",
        "tele": "0532-85959667"
      },
      {
        "name": "西院总值班室",
        "tele": "0532-85959998"
      },
      {
        "name": "西院保卫室（24小时）",
        "tele": "0532-85959110"
      },
      {
        "name": "西院校医院",
        "tele": "0532-85958230"
      },
      {
        "name": "西院图书馆",
        "tele": "0532-85959035"
      },
      {
        "name": "东院保卫室",
        "tele": "0532-88907355 "
      },
      {
        "name": "东院公寓",
        "tele": "0532-88909052"
      },
      {
        "name": "东院物业",
        "tele": "0532-88908719 "
      },
      {
        "name": "东院图书馆",
        "tele": "0532-85959321 "
      }]
    }]
  },
  
  godetail:function(e){
    var name = e.currentTarget.dataset.name;
    wx.setStorageSync('myname', name);
    var arr = e.currentTarget.dataset.arr;
    wx.setStorageSync('myarr', arr);
    
    wx.navigateTo({
      url: '../telephone/detail/detail',
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