let isAdded = false;

window.onload = function () {
    const template = document.getElementById("card-ques");
    document.getElementById("btn-add").onclick = function () {
        let count = parseInt(document.getElementById("res").value);
        let cardsDiv = document.getElementById("cards-ques");
        let cards = cardsDiv.getElementsByClassName("row-ques");
        let countCards = cards.length;
        if (isAdded != true) {
            this.textContent = "Обновить формы";
            let el = document.createElement("div");
            el.innerHTML = '<div class="row justify-content-center pb-3"><div class="col-6"><div class="card"><div class="card-body text-center"><h6>Заполните вопросы ниже</h6></div></div></div></div>';
            cardsDiv.parentElement.insertBefore(el, cardsDiv);
            el = document.createElement("div");
            el.innerHTML = '<div class="row justify-content-center pb-3"><div class="col-auto"><input class="btn btn-primary" type="submit" value="Начать заполнение вопросов" /></div></div>';
            cardsDiv.parentElement.append(el);
            isAdded = true;
        }
        if (count > countCards) {
            for (let i = countCards; i < count; i++) {
                let copy = template.content.cloneNode(true);
                let area = copy.querySelector('textarea');
                let name = area.getAttribute('name');
                area.setAttribute('name', name.replace(/#/, i));
                let input = copy.querySelector('.input-group');
                for (let j = 0; j < 3; j++) {
                    let input2 = input.cloneNode(true);
                    input.parentElement.append(input2);
                }
                let groups = copy.querySelectorAll('.input-group');
                groups.forEach((el, j) => {
                    let inputs = el.querySelectorAll('input');
                    let name = inputs[0].getAttribute('name');
                    inputs[0].setAttribute('name', name.replace(/#/, i));
                    inputs[0].value = j;
                    name = inputs[1].getAttribute('name');
                    name = name.replace(/#/, i);
                    inputs[1].setAttribute('name', name.replace(/&/, j));
                });
                cardsDiv.append(copy);
            }
        }
        else if (count < countCards) {
            while (cards[count])
                cards[count].remove();
        }
    };
}