<template>
  <body>
    <v-app class="globalHeader">
      <div class="scheduler-container">
        <Header class="globalHeader"/>
        <SchedulerHeader :title="scheduleTitle" :store="store"  />
        <v-date-picker
          v-if="store.showPicker"
          class="v-date-picker"
          :color="nowColor"
          v-model="now"
          width="28vw"
        />
        <OptionsBar :store="store"/>
        <v-calendar
          class="v-calendar"
          :color="nowColor"
          ref="calendar"
          :now="now"
          :value="now"
          :events="events"
          type="week"
          @change="updateEvents"
          @mousedown:event="startDrag"
          @mousedown:time="startTime"
          @mousemove:time="mouseMove"
          @mouseup:time="endDrag"
          @mouseleave.native="cancelDrag"
        >
          <template #event="{ event, timed, eventSummary }">
            <div v-if="event.canEdit" class="delete" @click="deleteEvent(event)"><BIconX></BIconX></div>
            <div
              class="v-event-draggable"
              v-html="eventSummary()"
            ></div>
            <div
              v-if="timed"
              class="v-event-drag-bottom"
              @mousedown.stop="extendBottom(event)"
            ></div>
          </template>
        </v-calendar>
        <BIconCloudArrowUpFill class="BIconCloudArrowUpFill" @click="updateEvents"></BIconCloudArrowUpFill>
      </div>
    </v-app>
  </body>
</template>

<script>
import Header from "../MemberHeader";
import SchedulerHeader from "./Header";
import OptionsBar from "./Options/OptionsBar";
import { BIconX, BIconCloudArrowUpFill } from 'bootstrap-vue';
import scheduleAPI from './scheduleAPI';
import { handleOptions } from "./Options/Handlers/rootHandler";

let store = {
  showPicker: false,
  toggleShowPicker: () => {
    store.showPicker = !store.showPicker;
  },
  selectedOption: '',
  selectOption: (option) => {
    if(option.role !== "DurationSelector") {
      store.selectedOption = option.role;
      store.options.forEach((opt) => {
        if (opt.role !== option.role) {
          opt.selected = '';
        } else {
          opt.selected = 'selected';
        }
      })
    }
  },
  selectSubOption: (option, subOption) => {
    store.options.filter((opt) => opt.role === option)[0].selectedSubOpt = subOption;
  }
}

export default {
  name: "Scheduler",
  props: ['variant'],
  components: {Header, SchedulerHeader, OptionsBar, BIconX, BIconCloudArrowUpFill},
  data: () => ({
    scheduleTitle: "Schedule",
    nowColor: "#31c9e0",
    store: store,
    now: (new Date(Date.now() - (new Date()).getTimezoneOffset() * 60000)).toISOString().substr(0, 10),
    events: [],
    colors: {
      "OH": "#71BF74",
      "Holidays": "#F05151",
      "Misc": "#516AF0",
    },
    names:  {
      "OH": "Open",
      "Holidays": "Holiday",
    },
    api: scheduleAPI,
    dragEvent: null,
    dragStart: null,
    createEvent: null,
    createStart: null,
    extendOriginal: null,
  }),
  watch: {
    // BruteForce reload: () => !time
    variant: function() {
      window.location.reload();
    }
  },
  created() {
    this.generateOptions();
  },
  mounted () {
    this.$refs.calendar.scrollToTime('07:45');
  },
  methods: {
    startDrag (event) {
      if (event.event && event.timed) {
        this.dragEvent = event.event;
        this.dragTime = null;
        this.extendOriginal = null;
      }
    },
    startTime (tms) {
      const mouse = this.toTime(tms);

      if (this.dragEvent && this.dragTime === null) {
        const start = this.dragEvent.start;

        this.dragTime = mouse - start
      } else if(this.colors[store.selectedOption]) {
        const option = store.options.filter((opt) => opt.role === store.selectedOption)[0];
        const preFix = store.selectedOption === "Misc" ? option.selectedSubOpt : "";
        const name = preFix + this.names[store.selectedOption];

        if (!this.checkOverlap(mouse, mouse, name)) {
          this.createStart = this.roundTime(mouse);
          this.createEvent = {
            res: option.res,
            userId: preFix === "" ? null : preFix,
            name:  preFix + this.names[store.selectedOption],
            color: this.colors[store.selectedOption],
            start: this.createStart,
            end: this.createStart,
            timed: true,
            canEdit: option.canEdit
          }
          this.events.push(this.createEvent);
        }
      }
    },
    extendBottom (event) {
        this.createEvent = event;
        this.createStart = event.start;
        this.extendOriginal = event.end;
    },
    mouseMove (tms) {
      const mouse = this.toTime(tms);

      if (this.dragEvent && this.dragTime !== null) {
        const start = this.dragEvent.start;
        const end = this.dragEvent.end;
        const duration = end - start;
        const newStartTime = mouse - this.dragTime;
        const newStart = this.roundTime(newStartTime);
        const newEnd = newStart + duration;

        this.dragEvent.start = newStart;
        this.dragEvent.end = newEnd;
      } else if (this.createEvent && this.createStart !== null) {
        const mouseRounded = this.roundTime(mouse, false);
        const min = Math.min(mouseRounded, this.createStart);
        const max = Math.max(mouseRounded, this.createStart);

        this.createEvent.start = min;
        this.createEvent.end = max;
      }
    },
    async endDrag () {
        const event = this.dragEvent || this.createEvent;
        if(event) {
          let overlap = this.checkOverlap(event.start, event.end, event.name);
          const duration = event.end - event.start;

          while(overlap) {
              event.end = overlap - 15 * 60 * 1000;
              event.start = overlap - 15 * 60 * 1000 - duration;
              overlap = this.checkOverlap(event.start, event.end, event.name);
          }

          if(event.name.includes("Booking")) {
            let maxLengthWrapper = store.options.filter((opt) => opt.role === "DurationSelector")[0];
            if(maxLengthWrapper) {
              let max_length =  parseInt(maxLengthWrapper.selectedSubOpt.substr(0,maxLengthWrapper.selectedSubOpt.length - 3));
              max_length *= 60 * 60 * 1000;

              if(duration > max_length) {
                event.end = event.start + max_length;
              }
            }
          }
        }
        this.dragTime = null;
        this.dragEvent = null;
        this.createEvent = null;
        this.createStart = null;
        this.extendOriginal = null;
    },
    cancelDrag () {
      if (this.createEvent) {
        if (this.extendOriginal) {
          this.createEvent.end = this.extendOriginal;
        } else {
          const i = this.events.indexOf(this.createEvent);
          if (i !== -1) {
            this.events.splice(i, 1);
          }
        }
      }

      this.createEvent = null;
      this.createStart = null;
      this.dragTime = null;
      this.dragEvent = null;
    },
    roundTime (time, down = true) {
      const roundTo = 15; // minutes
      const roundDownTime = roundTo * 60 * 1000;

      return down
        ? time - time % roundDownTime
        : time + (roundDownTime - (time % roundDownTime));
    },
    toTime (tms) {
      return new Date(tms.year, tms.month - 1, tms.day, tms.hour, tms.minute).getTime();
    },
    checkOverlap(start, end, name) {
      let overlap = null;
      let currentEvent = {
        name: name,
        res: store.options.filter((opt) => opt.role === store.selectedOption)[0].res,
        start: start,
        end: end,
      }

      this.events.forEach((e) => {
        if((this.checkName(e, currentEvent) || this.checkRes(e, currentEvent)) && this.checkTime(e, currentEvent)) {
          overlap = e.start;
        }
      });

      return overlap;
    },
    checkName(e1, e2) {
      return e1.name === "Holiday" ||
        e2.name === "Holiday" ||
        (e1.name === e2.name);
    },
    checkRes(e1, e2) {
      // Bookings can't overlap, even if their name doesn't match
      // (Name is user-related)
      return e1.res === "Booking" && e2.res === "Booking";
    },
    checkTime(e1, e2) {
      return (e1.start > e2.start && e1.start < e2.end) ||
        (e1.end > e2.start && e1.end < e2.end) ||
        (e2.start > e1.start && e2.start <  e1.end) ||
        (e2.end > e1.start && e2.end < e1.end);
    },
    deleteEvent(event) {
      this.events.splice(this.events.indexOf(event), 1);
      this.api.delete(event);
    },
    async updateEvents ({ end }) {
      this.currentDate = end || this.currentDate || new Date(Date.now() - (new Date()).getTimezoneOffset() * 60000);
      // Save Events
      await this.saveEvents();
      const bounds = this.computeWeekBounds(this.currentDate);

      // Load new events
      const miscOpt= store.options.filter((opt) => opt.role === "Misc")[0];
      this.events = await this.api.getEvents(store.options, bounds[0], bounds[1], miscOpt ? miscOpt.res : null);
    },
    async saveEvents () {
      for(const event of this.events) {
        event.id = await this.api.saveEvent(event);
      }
    },
    computeWeekBounds (day) {
      const date = new Date(day.date); // get current date
      const DAY_VALUE = 1000 * 60 * 60 * 24;
      const first = new Date(date.getTime() - (date.getDay() * DAY_VALUE));
      const last = new Date(date.getTime() + (6 - date.getDay()) * DAY_VALUE);
      first.setHours(0, 0, 0, 0);

      return [first.getTime(), last.getTime()];
    },
    generateOptions() {
      const userWrapper = handleOptions(this.variant, this.$router);
      this.scheduleTitle = userWrapper.scheduleTitle || "Scheduling";
      this.store.options = userWrapper.options;
      this.store.user = userWrapper.user;
      const miscOption = userWrapper.options.filter((opt) => opt.role === "Misc")[0];
      this.names["Misc"] = miscOption ? miscOption.suffix : "";
    }
  },
}


</script>

<style scoped>
  >>>.globalHeader a{
    color: whitesmoke;
  }
  .v-date-picker {
    z-index: 1000;
    position: absolute;
    padding-bottom:2rem;
    margin-left: -1.5vw;
    border: 0.2rem solid #2c3e50;
    border-radius:1rem;
  }
  .v-calendar {
    height: 34vw;
    width: 60vw;
    margin-left:20vw;
    box-sizing: border-box;
    border: 0.2rem solid #2c3e50;
    border-radius:1rem;
  }

  .delete {
    position:absolute;
    margin-left: -0.5rem;
    margin-top:-0.5rem;
    width:1rem;
    height: 1rem;
    background-color: #f1f731;
    color: #000000;
    border-radius: 100%;
    text-align: center;
  }

  .BIconCloudArrowUpFill {
    font-size: 2rem;
    margin-left: -1rem;
    margin-top: 0.3rem;
  }
  .BIconCloudArrowUpFill:hover {
    transform: scale(1.2);
    cursor: pointer;
  }
</style>


<style scoped lang="scss">
.v-event-draggable {
  padding-left: 6px;
}

.v-event-timed {
  user-select: none;
  -webkit-user-select: none;
}

.v-event-drag-bottom {
  position: absolute;
  left: 0;
  right: 0;
  bottom: 4px;
  height: 4px;
  cursor: ns-resize;

  &::after {
    display: none;
    position: absolute;
    left: 50%;
    height: 4px;
    border-top: 1px solid white;
    border-bottom: 1px solid white;
    width: 16px;
    margin-left: -8px;
    opacity: 0.8;
    content: '';
  }

  &:hover::after {
    display: block;
  }
}
</style>
