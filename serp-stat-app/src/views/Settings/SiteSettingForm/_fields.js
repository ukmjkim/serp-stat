export const FIELDS = new Map([
  ["id", {
    type: 'input',
    label: 'ID for Site',
    validate: (key, value) => {
      return { response: true };
    }
  }],
  ["title", {
    type: 'input',
    label: 'Title for Site',
    validate: (key, value) => {
      if (!value) {
        return { response: false, message: `Enter a ${key}` };
      } else {
        return { response: true };
      }
    }
  }],
  ["url", {
    type: 'input',
    label: 'Url for Site',
    validate: (key, value) => {
      if (!value) {
        return { response: false, message: `Enter a ${key}` };
      } else {
        return { response: true };
      }
    }
  }],
  ["non_ranking_value", {
    type: 'input',
    label: 'Non Ranking Value',
    validate: (key, value) => {
      return { response: true };
    }
  }],
  ["tracking", {
    type: 'checkbox',
    label: 'Tracking',
    validate: (key, value) => {
      return { response: true };
    }
  }],
  ["treat_non_ranking_as", {
    type: 'checkbox',
    label: 'Non Ranking Null',
    validate: (key, value) => {
      return { response: true };
    }
  }],
  ["drop_www_prefix", {
    type: 'checkbox',
    label: 'Drop WWW',
    validate: (key, value) => {
      return { response: true };
    }
  }],
  ["drop_directories", {
    type: 'checkbox',
    label: 'Drop Directories',
    validate: (key, value) => {
      return { response: true };
    }
  }],
]);
