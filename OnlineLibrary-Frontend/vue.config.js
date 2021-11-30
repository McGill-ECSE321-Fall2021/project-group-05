/**
 * @type {import('@vue/cli-service').ProjectOptions}
 */
module.exports = {
  pwa: {
    name: "Bookface",
    themeColor: "#118811",
    msTileColor: "#118811",
    appleMobileWebAppCapable: "yes",
    iconPaths:{
      favicon32: 'img/icons/favicon-32x32.png',
      favicon16: 'img/icons/favicon-16x16.png',
      appleTouchIcon: 'img/icons/apple-touch-icon.png',
      maskIcon: 'img/icons/safari-pinned-tab.svg',
      msTileImage: 'img/icons/mstile-150x150.png'
    }
  }
};
