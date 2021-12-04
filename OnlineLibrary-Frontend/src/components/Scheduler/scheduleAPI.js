'use strict'
import axios from "axios";
const config = require("../../../config");

const backendUrl =
  process.env.NODE_ENV === "production"
    ? `https://${config.build.backendHost}`
    : `http://${config.dev.backendHost}:${config.dev.backendPort}`;
const frontendUrl =
  process.env.NODE_ENV === "production"
    ? `https://${config.build.host}`
    : `http://${config.dev.host}:${config.dev.port}`;

const AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { "Access-Control-Allow-Origin": frontendUrl }
});

const _getRoomId = () => {
  return AXIOS.get('/room/')
    .then(response => {
      return response.data[0].id;
    })
    .catch(e => {
      return '';
    })
}

const _pad = (time) => {
  if(time < 10) {
    return "0"+time;
  }

  return time;
}

const _formatTime = (start, end) => {
  const dateTime = {
    start: new Date(start),
    end: new Date(end)
  }
  const dates = {
    startDate: dateTime.start.getUTCFullYear() + "-"
      + _pad(dateTime.start.getUTCMonth() + 1)
      + "-" + _pad(dateTime.start.getUTCDate()),
    endDate: dateTime.end.getUTCFullYear() + "-"
      + _pad(dateTime.end.getUTCMonth() + 1)
      + "-" + _pad(dateTime.end.getUTCDate())
  }
  const times = {
    startTime: _pad(dateTime.start.getHours()) + ":"
      + _pad(dateTime.start.getMinutes()) + ":"
      + _pad(dateTime.start.getSeconds()),
    endTime:  _pad(dateTime.end.getHours()) + ":"
      + _pad(dateTime.end.getMinutes()) + ":"
      + _pad(dateTime.end.getSeconds())
  }

  return { dates, times };
}

const _deduceResource = async (event) => {
  const { dates, times } = _formatTime(event.start, event.end)

  let resources = {
    "booking" : {
      path: "/roomBooking",
      body: {
        date: dates.startDate,
        ...times,
        roomId: await _getRoomId(),
        memberId: event.userId
      }
    },
    "shift" : {
      path: "/librarianShift",
      subPath: "/between",
      body: {
        date: dates.startDate,
        ...times,
        librarianId: event.userId
      }
    },
    "holidays" : {
      path: "/holiday",
      body: {
        name: event.name,
        ...dates
      }
    },
    "opening hours" : {
      path: "/libraryOpeningHours",
      body: {
        date: dates.startDate,
        ...times
      }
    },
  }

  let resource = resources[event.res.toLowerCase()];
  // Looking for invalid resource
  if(!resource) {
    return resource;
  }

  resource  = {
    id: event.id, // Delete
    ...resource,  // Path & Post
    ...dates      // Get
  }

  return resource;
}

const _createEventDTO = (event, res) => {
  switch(res.toLowerCase()) {
    case "holidays":
      return _createHolidayDTO(event);
    case "shift":
      return _createShiftDTO(event);
    case "booking":
      return _createBookingDTO(event);
    default:
      return _createOH_DTO(event);
  }
}

const _createHolidayDTO = (event) => {
  return {
    name: "Holiday",
    color: "#F05151",
    start: (new Date(event.startDate + "T01:00:00")).getTime(),
    end: (new Date(event.startDate + "T23:00:00")).getTime(),
  }
}

const _createOH_DTO = (event) => {
  return {
    name: "Open",
    color: "#71BF74",
    start: (new Date(event.date + "T" + event.startTime)).getTime(),
    end: (new Date(event.date + "T" + event.endTime)).getTime(),
  }
}

const _createBookingDTO = (event) => {
  return {
    id: event.id,
    userId: event.member.id,
    name: event.member.id + "'s booking",
    color: "#516AF0",
    start: (new Date(event.date + "T" + event.startTime)).getTime(),
    end: (new Date(event.date + "T" + event.endTime)).getTime(),
  }
}

const _createShiftDTO = (event) => {
  console.log(JSON.stringify(event))
  return {
    id: event.id,
    userId: event.librarianId,
    name: event.librarianId + "'s shift",
    color: "#516AF0",
    start: (new Date(event.date + "T" + event.startTime)).getTime(),
    end: (new Date(event.date + "T" + event.endTime)).getTime(),
  }
}

const _createDTO = (event, res, options) => {
  const option = options.filter((opt) => opt.res.toLowerCase() === res.toLowerCase());

  return {
    id: event.id,
    res: res,
    ..._createEventDTO(event, res),
    timed: true,
    canEdit: option ? option[0].canEdit : false
  }
}

const scheduleAPI = {
  async saveEvent(event) {
    // Delete first (poor's man patch)
    if(event.userId) {
      await this.delete(event);
    }

    // Create anew
    const resource = await _deduceResource(event);
    if(resource) {
      return AXIOS.post(resource.path, null, {params: resource.body})
        .then(response => {
          return response.data.id;
        })
        .catch(e => {
          return null;
        })
    }
  },
  async getEvents(options, start, end, misc) {
    let resource = {};
    let responses = [];
    let errors = [];
    let promises = [];
    let events = ['Holidays', 'Opening Hours']

    if(misc) {
      events.push(misc);
    }

    for (const event of events) {
      resource = await _deduceResource({
        res: event,
        start: new Date(start).getTime(),
        end: new Date(end).getTime()
      });

      if(resource) {
        promises.push(
          AXIOS.get(resource.path + (resource.subPath || "") + '/' + resource.startDate + '/' + resource.endDate)
            .then( response => {
              response.data.forEach( entry => responses.push(_createDTO(entry, event, options)));
            })
            .catch( e => {
              errors.push(e);
            })
        )
      }
    }

    return Promise.all(promises).then(() => responses.length !== 0 ? responses : errors);
  },
  async delete(event) {
    let resource = await _deduceResource(event);

    try {
      if(resource && resource.path && resource.id) {
        await AXIOS.delete(resource.path + "/" + resource.id);
      }
    } catch (e) {} // Nothing to delete in the backend
  },
  getLibrarians() {
    return AXIOS.get('/librarian/all')
      .then(response => {
        return response.data;
      })
      .catch(e => {
        return [];
      })
  },
  getMembers() {
    return AXIOS.get('/member/all')
      .then(response => {
        return response.data;
      })
      .catch(e => {
        return [];
      })
  }
}

export default scheduleAPI;
