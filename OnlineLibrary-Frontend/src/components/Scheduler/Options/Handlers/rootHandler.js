import bookingLibrarianHead from '../../../../assets/Copy/RoomBooking/headLibrarian.json';
import bookingLibrarian from '../../../../assets/Copy/RoomBooking/librarian.json';
import bookingMember from '../../../../assets/Copy/RoomBooking/member.json';
import scheduleLibrarianHead from '../../../../assets/Copy/Schedule/headLibrarian.json';
import scheduleLibrarian from '../../../../assets/Copy/Schedule/librarian.json';
import scheduleMember from '../../../../assets/Copy/Schedule/member.json';
import scheduleAPI from '../../scheduleAPI';

const _findUser = (variant, router, imports) => {
  let user = {};
  let options = [];

  if(sessionStorage['loggedInMember']) {
    user = JSON.parse(sessionStorage.getItem("loggedInMember")).member;
    options = imports[2];
    const miscOpt = options.options.filter((opt) => opt.role === "Misc")[0];
    if(miscOpt && variant === "booking") {
      miscOpt.selectedSubOpt = user.id;
      miscOpt.subOpt = [user.id];
    }
  } else if (sessionStorage['loggedInLibrarian']) {
    user = JSON.parse(sessionStorage.getItem("loggedInLibrarian")).librarian;
    options = user.head ? imports[0] : imports[1];
    const miscOpt = options.options.filter((opt) => opt.role === "Misc")[0];
    if(miscOpt && variant === "booking") {
      scheduleAPI.getMembers().then(
        members => {
          miscOpt.selectedSubOpt = members[0].id;
          miscOpt.subOpt = members.map(member => member.id);
        }
      )
    } else if(miscOpt && variant === "schedule"){
      miscOpt.selectedSubOpt = user.id;
      scheduleAPI.getLibrarians().then(librarians => miscOpt.subOpt = librarians.map(lib => lib.id));

    }
  } else {
    router.push({ name: "NotFound" });
  }

  return { user: user, options: options.options, scheduleTitle: options.scheduleTitle };
}

export const handleOptions = (resource, router) => {
  let userWrapper = {}

  switch(resource) {
    case 'Schedule':
      userWrapper = _findUser("schedule", router, [scheduleLibrarianHead, scheduleLibrarian, scheduleMember]);
      break;
    case 'RoomBooking':
      userWrapper = _findUser("booking", router, [bookingLibrarianHead, bookingLibrarian, bookingMember]);
      break;
  }

  return userWrapper;
}

