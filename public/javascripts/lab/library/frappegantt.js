'use strict';

$(document).ready(function() {

	var jsdate = moment(messages("clientDateTime")).toDate();

	var names = [
			["Redesign website", [0, 7]],
			["Write new content", [1, 4]],
			["Apply new styles", [3, 6]],
			["Review", [7, 7]],
			["Deploy", [8, 9]],
			["Go Live!", [10, 10]]
	];

	var tasks = names.map(function(name, i) {
			var today = jsdate;
			var start = new Date(today.getFullYear(), today.getMonth(), today.getDate());
			var end = new Date(today.getFullYear(), today.getMonth(), today.getDate());
			start.setDate(today.getDate() + name[1][0]);
			end.setDate(today.getDate() + name[1][1]);
			return {
					start: start,
					end: end,
					name: name[0],
					id: "Task " + i,
					progress: parseInt(Math.random() * 100, 10)
			}
	});
	tasks[1].dependencies = "Task 0"
	tasks[2].dependencies = "Task 1"
	tasks[3].dependencies = "Task 2"
	tasks[5].dependencies = "Task 4"

	var gantt = new Gantt("#gantt", tasks, {
		on_click: function (task) {
				console.log(task);
		},
		on_date_change: function(task, start, end) {
				console.log(task, start, end);
		},
		on_progress_change: function(task, progress) {
				console.log(task, progress);
		},
		on_view_change: function(mode) {
				console.log(mode);
		},
		custom_popup_html: function(task) {
			const end_date = task._end.format('MMM D');
  			return '' +
				'<div class="details-container">' +
					'<h5>' + task.name + '</h5>' +
					'<p>Expected to finish by ' + end_date + '</p>' +
					'<p>' + task.progress + '% completed!</p>' +
				'</div>' +
			'';
		}
	});
	gantt.change_view_mode('Week');

	$("#ganttViewSelect").on("click", "button", function() {
			var $btn = $(this);
			var mode = $btn.data('duration');
			gantt.change_view_mode(mode);
			$btn.parent().find('button').removeClass('active');
			$btn.addClass('active');
	});
});
