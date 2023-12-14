use std::{cmp, iter};
use std::collections::HashMap;
use std::fs::File;
use std::io::{self, BufRead};
use std::path::Path;
use std::i32::MAX;

fn main() {

    if let Ok(lines) = read_lines("./input.txt") {
        let mut expanded_galaxy: Vec<Vec<char>> = vec![];
        let mut galaxy_points: Vec<(i32, i32)> = vec![];
        // key --> ((host), (visited))
        let mut visited : HashMap::<((i32, i32),(i32, i32)), bool> = HashMap::new();
        let mut columns : HashMap::<i32, bool> = HashMap::new();
        let mut rows: HashMap::<i32, bool> = HashMap::new();
        let mut line_len = MAX;
        let mut line_idx = 0;
        for line in lines {
            if let Ok(line_str) = line {
                let char_vec = line_str.chars().collect::<Vec<char>>();
                line_len = char_vec.len() as i32;
                let galaxies:Vec<usize> = line_str
                    .char_indices()
                    .filter_map(|(index, c)| if c == '#' { Some(index) } else { None })
                    .collect();

                if galaxies.len() == 0 {
                    rows.insert(line_idx, true);
                } else {
                    for galaxy in galaxies {
                        columns.insert(galaxy as i32, true);
                    }
                }
                expanded_galaxy.push(char_vec);
            }
            line_idx += 1;
        }

        let mut indexes_to_add = vec![];
        for i in 0..line_len {
            if !columns.contains_key(&i){
                indexes_to_add.push(i);
            }
        }

        indexes_to_add.sort();
        indexes_to_add.reverse();


        for (line_index, line) in &mut expanded_galaxy.iter_mut().enumerate() {
            for (i, character) in line.iter().enumerate() {
                if character == &'#' {
                    galaxy_points.push((i as i32, line_index as i32));
                }
            }
        }
        
        let mut ans: u64 = 0;
        for galaxy_point_origin in &galaxy_points {
            for next_point in &galaxy_points {
                if *galaxy_point_origin == *next_point || visited.contains_key(&(*next_point, *galaxy_point_origin)) {
                    continue;
                }
                visited.insert((*galaxy_point_origin, *next_point), true);

                let mut millions = 0; 
                for i in cmp::min(galaxy_point_origin.0, next_point.0)..cmp::max(galaxy_point_origin.0, next_point.0){
                    if !columns.contains_key(&i){
                        millions += 1;
                    }
                }

                for i in cmp::min(galaxy_point_origin.1, next_point.1)..cmp::max(galaxy_point_origin.1, next_point.1){
                    if rows.contains_key(&i){
                        millions += 1;
                    }
                }

                ans += galaxy_point_origin.0.abs_diff(next_point.0) as u64 + galaxy_point_origin.1.abs_diff(next_point.1) as u64 + 999_999 * millions;
            }
        }

        println!("Answer: {}", ans);
    }
}

// The output is wrapped in a Result to allow matching on errors
// Returns an Iterator to the Reader of the lines of the file.
fn read_lines<P>(filename: P) -> io::Result<io::Lines<io::BufReader<File>>>
where P: AsRef<Path>, {
    let file = File::open(filename)?;
    Ok(io::BufReader::new(file).lines())
}
