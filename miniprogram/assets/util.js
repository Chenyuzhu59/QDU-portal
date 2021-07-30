const formatNumber = n =>{
  n = n.toString()
  return n[1]? n : '0'+ n
}

const formatDate = date => {

  const year = date.getFullYear()

  const month = date.getMonth() + 1

  const day = date.getDate()

  return [year, month, day].map(formatNumber).join('-')

}


function getDates(dates) {

  let dateObj = {};

  let show_day = new Array('7', '1', '2', '3', '4', '5', '6');
  let show_day2= new Array('Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat');
  let date = new Date(dates);

  let day = date.getDay();

  dateObj.time = dates;

  dateObj.xqj = show_day[day];

  dateObj.xqjformat = show_day2[day];

  return dateObj;

}

module.exports = {

  formatDate: formatDate,

  getDates: getDates

}