export default {
  items: [
    {
      name: 'Dashboard',
      url: '/dashboard',
      icon: 'icon-speedometer',
      badge: {
        variant: 'info',
        text: 'NEW'
      }
    },
    {
      title: true,
      name: 'WayFair.ca',
      wrapper: {            // optional wrapper object
        element: '',        // required valid HTML5 element tag
        attributes: {}        // optional valid JS object with JS API naming ex: { className: "my-class", style: { fontFamily: "Verdana" }, id: "my-id"}
      },
      class: ''             // optional class names space delimited list for title item ex: "text-center"
    },
    {
      name: 'Top10 Keywords',
      url: '/site/1/tag/1',
      icon: 'icon-puzzle'
    },
    {
      name: 'High Revenue',
      url: '/site/1/tag/2',
      icon: 'icon-star'
    },
    {
      name: 'Seasonal',
      url: '/site/1/tag/3',
      icon: 'icon-calculator'
    },
    {
      name: 'Targeting',
      url: '/site/1/tag/4',
      icon: 'icon-pie-chart'
    },
    {
      name: 'Customer Service',
      url: '/customer_service',
      icon: 'icon-cloud-download',
      class: 'mt-auto',
      variant: 'success'
    },
    {
      name: 'What\'s New',
      url: '/whats_new',
      icon: 'icon-layers',
      variant: 'danger'
    }
  ]
};
