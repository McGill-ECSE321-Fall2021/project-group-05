<template>
  <div class="option" :class="[details.role, details.selected]">
    <span class="optionTitle" @click="handleOptionClick(details)">
      {{ details.res }}<span v-if="details.expands">:</span>
      {{ details.selectedSubOpt}}
      <BIconCaretDownFill v-if="details.expands && details.canEdit"/>
    </span>
    <div class="searchWrapper" v-if="details.expands && details.subOpt && showSubOptions" >
      <input v-debounce:400ms="filterSubOptions" class="searchBox" type="text" placeholder="Search..."/>
      <ul v-if="details.subOpt && showSubOptions" >
        <SubOption v-for="subOpt in getSubOptions()" v-bind:key="subOpt" :details="subOpt" @click="handleSubOptionClick(details.role, subOpt)" />
      </ul>
    </div>
  </div>
</template>

<script>
import { BIconCaretDownFill } from 'bootstrap-vue';
import SubOption from './SubOption';

export default {
  name: "Option",
  props: ['details', 'store'],
  components: {
    BIconCaretDownFill,
    SubOption
  },
  data: () => ({
    showSubOptions: false,
    currentSubOptions: null,
  }),
  watch: {
    details: {
      handler(value) {
        this.currentSubOptions = value.subOpt;
      }
    }
  },
  methods: {
    handleOptionClick(details) {
      if(details.canEdit) {
        this.showSubOptions = !this.showSubOptions;
        this.store.selectOption(details);
      }
    },
    handleSubOptionClick(role, subOpt) {
      if(this.details.canEdit) {
        this.showSubOptions = false;
        this.store.selectSubOption(role, subOpt);
      }
    },
    filterSubOptions(input)  {
      this.currentSubOptions = this.details.subOpt.filter((subOpt) => subOpt.toString().toLowerCase().includes(input.toLowerCase()));
    },
    getSubOptions() {
      return this.currentSubOptions || this.details.subOpt;
    }
  }
}
</script>

<style scoped>
  .option {
    display:table-cell;
    min-width: 10rem;
    margin-left: 1rem;
    padding-top: 0.2rem;
    padding-bottom:0.2rem;
    vertical-align: middle;
    text-align: center;
    font-weight: bold;
    color: #f2ebeb;
    opacity: 0.3;
    border-radius: 3rem;
    text-overflow: ellipsis;
    overflow: hidden;
  }
  .option:hover {
    opacity: 1;
  }
  .optionTitle {
    cursor: pointer;
    white-space: nowrap;
    text-overflow: ellipsis;
  }
  .OH {
    background-color: #71BF74;
  }
  .Holidays {
    background-color: #F05151;
  }
  .Misc {
    background-color: #516AF0;
  }
  .DurationSelector {
    color: #2c3e50;
    opacity: 1;
  }
  .selected {
    opacity: 1;
    color:white;
  }
  .searchWrapper {
    z-index: 999;
    position: absolute;
    width: 10rem;
    max-height: 35vh;
    padding:0rem;
    margin-top: 0.5vw;
    box-sizing: border-box;
    text-align: center;
  }
  .searchBox {
    width: 10rem;
    background-color: #31c9e0;
    color:#ffffff;
    padding: 1rem;
    border: 0.2rem solid #2c3e50;
    border-radius:  1rem 1rem 0px 0px;
  }
  .searchBox::placeholder {
    color:#ffffff;
    text-align: center;
  }
  ul {
    overflow-y:scroll;
    max-height: 25vh;
    -ms-overflow-style: none;  /* IE and Edge */
    scrollbar-width: none;  /* Firefox */
    box-sizing: border-box;
    padding:1rem;
    border: 0.2rem solid #2c3e50;
    border-top:0px;
    border-radius: 0px 0px 1rem 1rem;
    background-color: #ffffff;
  }
  ul::-webkit-scrollbar {
    display: none; /* Chrome */
  }
</style>
