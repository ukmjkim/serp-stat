import { SubmissionError } from 'redux-form'

const sleep = ms => new Promise(resolve => setTimeout(resolve, ms))

function submit(values) {
  return sleep(1000) // simulate server latency
    .then(() => {
      window.alert(`You submitted:\n\n${JSON.stringify(values, null, 2)}`)
    })
}

export default submit
