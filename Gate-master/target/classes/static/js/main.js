const exam = {
    "teacherId": "1307",
    "tasks": [
        {
            "question": "Find catheter (rounded to the nearest integer)",
            "pictureUrl": "https://www.smashingmagazine.com/wp-content/uploads/2015/06/10-dithering-opt.jpg",
            "options": [],
            "answer": [
                "2"
            ]
        },
        {
            "question": "Find catheter (rounded to the nearest integer)",
            "options": [],
            "answer": [
                "2"
            ]
        },
        {
            "question": "Find catheter (rounded to the nearest integer)",
            "options": ["First option", "Second option"],
            "answer": [
                "1"
            ]
        }
    ]
};

document.querySelector('#examIdSubmit').addEventListener('click', () => {
    hideExamSelectorForm();
    fetchExam().then(showExam).catch((err) => {
        document.write(JSON.stringify(err));
    });
});

function hideExamSelectorForm() {
    document.querySelector('#examSelectorForm').style.display = 'none';
}

let teacherId = '';
let examId = '';
let studentName = '';

function fetchExam() {
    teacherId = document.querySelector('#teacherIdInput').value;
    examId = document.querySelector('#examIdInput').value;
    studentName = document.querySelector('#studentNameInput').value;

    return fetch(`/exam/getExam?teacherId=${teacherId}&examId=${examId}`)
        .then(res => res.json());
}

function showExam(exam) {
    document.querySelector('#examForm').style.display = 'block';
    const taskInformations = exam.tasks.map(addTask);

    const msStart = Date.now();
    document.querySelector('#examSubmit').addEventListener('click', () => {
        const msEnd = Date.now();
        const seconds = (msEnd - msStart) / 1000;
        let numberOfSkippedAnswers = 0;
        let numberOfWrongAnswers = 0;

        taskInformations.forEach(info => {
            if (info.isSkipped()) {
                numberOfSkippedAnswers++;
            } else if (!info.isCorrect()) {
                numberOfWrongAnswers++;
            }
        });

        const result = {
            examId,
            teacherId,
            numberOfSkippedAnswers,
            numberOfWrongAnswers,
            seconds,
            name: studentName,
        };

        fetch('/exam/sendResult', {
            method: 'post',
            headers: {
                "Content-type": "application/json; charset=UTF-8"
            },
            body: JSON.stringify(result),
        })
            .catch((err) => console.error(JSON.stringify(err)))
            .then(() => showResult(result, taskInformations.length));
    });
}

function showResult(examInfo, total) {
    console.log('here');
    document.querySelector('#examForm').style.display = 'none';
    let element = document.querySelector('#examResult');
    element.style.display = 'block';
    element.querySelector('#seconds').innerText = examInfo.seconds + ' |';
    element.querySelector('#correct').innerText = total - examInfo.numberOfSkippedAnswers - examInfo.numberOfWrongAnswers + ' |';
    element.querySelector('#wrong').innerText = examInfo.numberOfWrongAnswers + ' |';
    element.querySelector('#skipped').innerText = examInfo.numberOfSkippedAnswers + ' |';
    element.querySelector('#total').innerText = total + '';
}

function addTask(task) {
    if (task.pictureUrl) {
        return addPictureTask(task);
    } else if (task.options && task.options.length) {
        return addOptionsTask(task);
    } else {
        return addSimpleTask(task);
    }
}

const taskViewsContainer = document.querySelector('#taskViewsContainer');

function addPictureTask(task) {
    const taskTemplate = taskViewsContainer.querySelector('.task-image-template').cloneNode(true);
    taskTemplate.querySelector('.task-question').innerText = task.question;
    taskTemplate.querySelector('.task-image').src = task.pictureUrl;
    document.querySelector('#tasksContainer').appendChild(taskTemplate);
    return {
        isCorrect: () => taskTemplate.querySelector('.task-answer').value === task.answer[0],
        isSkipped: () => taskTemplate.querySelector('.task-skip').checked,
    };
}

function addSimpleTask(task) {
    const taskTemplate = taskViewsContainer.querySelector('.task-simple-template').cloneNode(true);
    taskTemplate.querySelector('.task-question').innerText = task.question;
    document.querySelector('#tasksContainer').appendChild(taskTemplate);
    return {
        isCorrect: () => taskTemplate.querySelector('.task-answer').value === task.answer[0],
        isSkipped: () => taskTemplate.querySelector('.task-skip').checked,
    };
}

function addOptionsTask(task) {
    const taskTemplate = taskViewsContainer.querySelector('.task-options-template').cloneNode(true);
    taskTemplate.querySelector('.task-question').innerText = task.question;
    const optionsContainer = taskTemplate.querySelector('.form-options');

    const answers = [];

    for (const option of task.options) {
        const optionTemplate = taskViewsContainer.querySelector('.task-options-option-template').cloneNode(true);
        optionTemplate.querySelector('.task-option-label').innerText = option;
        optionsContainer.appendChild(optionTemplate);

        answers.push(() => optionTemplate.querySelector('.task-option-input').checked);
    }
    document.querySelector('#tasksContainer').appendChild(taskTemplate);

    return {
        isCorrect: () => {
            let isCorrect = true;
            const answerIndices = task.answer.map(index => +index);
            answers.forEach((answer, index) => {
                if (answer()) {
                    if (answerIndices.indexOf(index) === -1) {
                        isCorrect = false;
                        return false;
                    }
                } else {
                    if (answerIndices.indexOf(index) !== -1) {
                        isCorrect = false;
                        return false;
                    }
                }
            });
            return isCorrect;
        },
        isSkipped: () => taskTemplate.querySelector('.task-skip').checked,
    };
}
