var stack_bottomright = {"dir1": "up", "dir2": "left", "firstpos1": 25, "firstpos2": 25};
function error(content) {
	var opts = {
			text: content,
			addclass: "stack-bottomright",
			stack: stack_bottomright,
			buttons: { sticker: false },
			hide: false,
			delay: 3000
		};
	opts.type = 'error';
	new PNotify(opts);
}
function success(content) {
	var opts = {
			text: content,
			addclass: "stack-bottomright",
			stack: stack_bottomright,
			buttons: { sticker: false },
			hide: true,
			delay: 3000
		};
	opts.type = 'success';
	new PNotify(opts);
}
function info(content) {
	var opts = {
			text: content,
			addclass: "stack-bottomright",
			stack: stack_bottomright,
			buttons: { sticker: false },
			hide: true,
			delay: 3000
		};
	opts.type = 'info';
	new PNotify(opts);
}
function notice(content) {
	var opts = {
			text: content,
			addclass: "stack-bottomright",
			stack: stack_bottomright,
			buttons: { sticker: false },
			hide: true,
			delay: 3000
		};
	new PNotify(opts);
}