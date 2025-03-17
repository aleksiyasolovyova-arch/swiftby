window.onload = function () {
    var charts = [];
    var toolTip = {shared: true},
        legend = {
            cursor: "pointer",
            itemclick: function (e) {
                e.dataSeries.visible = !e.dataSeries.visible;
                e.chart.render();
            }
        };

    var voltageDps = [], currentDps = [], tempDps = [];

    var batteryChartOptions = {
        animationEnabled: true,
        theme: "light2",
        title: {text: "Battery Metrics"},
        axisY: {title: "Values", suffix: " V / A / °C"},
        toolTip: toolTip,
        legend: legend,
        data: [
            {
                type: "spline",
                showInLegend: true,
                name: "Voltage (V)",
                color: "#ff9800",
                xValueType: "dateTime",
                dataPoints: voltageDps
            },
            {
                type: "spline",
                showInLegend: true,
                name: "Current (A)",
                color: "#2196f3",
                xValueType: "dateTime",
                dataPoints: currentDps
            },
            {
                type: "spline",
                showInLegend: true,
                name: "Temperature (°C)",
                color: "#e53935",
                xValueType: "dateTime",
                dataPoints: tempDps
            }
        ]
    };

    charts.push(new CanvasJS.Chart("chartContainer", batteryChartOptions));

    //TODO fix this so this fetches the other API
    fetch("/api/csv/Dummy_data_e_bike_testbench_Data.csv")
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            return response.text();
        })
        .then(data => {
            console.log("CSV Data Loaded:", data);
            var rows = data.split("\n");
            for (var i = 1; i < rows.length; i++) {
                var cols = rows[i].split(",");
                if (cols.length >= 5) {
                    var time = new Date(cols[0]).getTime();
                    voltageDps.push({x: time, y: parseFloat(cols[1])});
                    currentDps.push({x: time, y: parseFloat(cols[2])});
                    tempDps.push({x: time, y: parseFloat(cols[4])});
                }
            }
            charts[0].render();
        })
        .catch(error => console.error('Error fetching CSV:', error));

};


const canvas = document.getElementById('progressCanvas');
const ctx = canvas.getContext('2d');

const centerX = canvas.width / 2;
const centerY = canvas.height / 2;
const radius = 80;
const startAngle = -Math.PI / 2;
const endAngle = Math.PI * 2;
const lineWidth = 15;

const progressPercentage = 45;

function drawProgress() {
    ctx.beginPath();
    ctx.arc(centerX, centerY, radius, startAngle, endAngle);
    ctx.strokeStyle = '#e0e0e0';
    ctx.lineWidth = lineWidth;
    ctx.stroke();

    const progressEndAngle = startAngle + (endAngle * (progressPercentage / 100));
    ctx.beginPath();
    ctx.arc(centerX, centerY, radius, startAngle, progressEndAngle);
    ctx.strokeStyle = '#007bff';
    ctx.lineWidth = lineWidth;
    ctx.stroke();

    ctx.beginPath();
    ctx.arc(centerX, centerY, radius + lineWidth / 2, startAngle, endAngle);
    ctx.strokeStyle = '#000000';
    ctx.lineWidth = 2;
    ctx.stroke();
}

function updateProgressDetails() {
    document.getElementById('testsDone').textContent = '4 / 11';
    document.getElementById('progressPercentage').textContent = `${progressPercentage}% Progress`;
}

drawProgress();
updateProgressDetails();
