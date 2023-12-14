use std::collections::HashMap;
use std::fs::File;
use std::io::{self, BufRead};
use std::path::Path;

fn main() {

    let directon_map = HashMap::from([
        ('|', (1, 0, 1, 0)),
        ('-', (0, 1, 0, 1)),
        ('L', (1, 1, 0, 0)),
        ('J', (1, 0, 0, 1)),
        ('7', (0, 0, 1, 1)),
        ('F', (0, 1, 1, 0)),]);

    if let Ok(lines) = read_lines("./input.txt") {
        let mut pipe_map: Vec<Vec<char>> = vec![];
        let mut start: (i32, i32) = (0, 0);

        let mut line_pos: i32 = 0;
        for line in lines {
            if let Ok(line_str) = line {

                let chars:Vec<char> = line_str.chars().collect();
                if let Some(start_index) = chars.iter().position(|&r| r == 'S'){
                    start = (start_index as i32, line_pos);
                }
                pipe_map.push(chars);
            }
            line_pos += 1;
        }

        println!("Start: ({}, {})", start.0, start.1);

        // ((x, y), distance, (entry_x, entry_y)
        let mut probe_0 = (start, 0, (0, 0));
        let mut probe_1 = (start, 0, (0, 0));

        let directions = [
            ((0, -1), "|F7"),
            ((1, 0), "-J7"),
            ((0, 1), "|LJ"),
            ((-1, 0), "-LF")];

        for direction in directions {
            if direction.1.contains(pipe_map[(start.1 as i32 + direction.0.1) as usize][(start.0 as i32 + direction.0.0) as usize]){
                if probe_0.1 == 0 {
                    probe_0 = (((start.0 as i32 + direction.0.0), (start.1 as i32 + direction.0.1)), 1, (-direction.0.0, -direction.0.1));
                } else {
                    probe_1 = (((start.0 as i32 + direction.0.0), (start.1 as i32 + direction.0.1)), 1, (-direction.0.0, -direction.0.1));
                }
            }
        }

        let mut i = 0;
        while probe_0.0 != probe_1.0 {
            println!("Probe_0: ({}, {}), Probe_1: ({}, {})", probe_0.0.0, probe_0.0.1, probe_1.0.0, probe_1.0.1);
            i += 1;
            // Next probe position = current probe position + directional movement
            // directional movement = the non-entry direction of the current pipe + 1
            // non-entry direction = current char direction options - entry direction
            for probe in [&mut probe_0, &mut probe_1].iter_mut() {
                let pipe = pipe_map[probe.0.1 as usize][probe.0.0 as usize];
                //println!("Checking: {}", pipe);
                let entries = *directon_map.get(&pipe).unwrap();
                let entry_direction = (
                    if probe.2.1 == -1 {1} else {0},
                    if probe.2.0 == 1 {1} else {0},
                    if probe.2.1 == 1 {1} else {0},
                    if probe.2.0 == -1 {1} else {0},
                );
                let next_direction = (
                    entries.0 - entry_direction.0,
                    entries.1 - entry_direction.1,
                    entries.2 - entry_direction.2,
                    entries.3 - entry_direction.3,
                );
                //println!("Next direction: ({}, {}, {}, {})", next_direction.0,next_direction.1,next_direction.2,next_direction.3);
        
                probe.0.0 = probe.0.0 + if next_direction.1 == 1 {1} else if next_direction.3 == 1 {-1} else {0};
                probe.0.1 = probe.0.1 + if next_direction.0 == 1 {-1} else if next_direction.2 == 1 {1} else {0};

                probe.1 += 1;
                probe.2 = (if next_direction.1 == 1 {-1} else if next_direction.3 == 1 {1} else {0}, if next_direction.0 == 1 {1} else if next_direction.2 == 1 {-1} else {0});
            }
        
        }
        println!("Steps: {}", probe_0.1);
    }
}

// The output is wrapped in a Result to allow matching on errors
// Returns an Iterator to the Reader of the lines of the file.
fn read_lines<P>(filename: P) -> io::Result<io::Lines<io::BufReader<File>>>
where P: AsRef<Path>, {
    let file = File::open(filename)?;
    Ok(io::BufReader::new(file).lines())
}
